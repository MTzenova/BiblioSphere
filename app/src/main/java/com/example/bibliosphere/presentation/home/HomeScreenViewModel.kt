package com.example.bibliosphere.presentation.home

import android.util.Log
import androidx.compose.runtime.key
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.genreBooksList
import com.example.bibliosphere.presentation.firebase.BookFirestoreRepository
import com.example.bibliosphere.presentation.firebase.BookStatusFS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel : ViewModel(){

    private val db = FirebaseFirestore.getInstance()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val bookFirestoreRepository = BookFirestoreRepository(db = FirebaseFirestore.getInstance(),
        api = RetrofitModule.api)

    private val _bookStatusFS = MutableLiveData<List<BookStatusFS>>()
    val bookStatusFS: LiveData<List<BookStatusFS>> = _bookStatusFS

    private val _topFiveBooks = MutableLiveData<List<Map<String, Any>>>()
    val topFiveBooks: LiveData<List<Map<String, Any>>> = _topFiveBooks

    private val _bookGenre = MutableLiveData<List<String>>()
    val bookGenre: LiveData<List<String>> = _bookGenre

    private val _randomBooks = MutableLiveData<List<Map<String, Any>>>()
    val randomBooks: LiveData<List<Map<String, Any>>> = _randomBooks

    private val key = "AIzaSyBF7rERYx2M8miJEyYZlxDFjSywpLhnHmU"

    fun getStatusBooks() {

        userId?.let {  id ->
            viewModelScope.launch {
                val status = bookFirestoreRepository.getStatusBookFS(id)
                _bookStatusFS.value = status
               Log.d("HomeScreenViewModel", "Datos obtenidos: $status")
            }
        }
    }

    fun getTopFiveBooks(search: String) {

        _isLoading.value = true

        viewModelScope.launch {
            //vamos a guardarlo en firestore para no llamar 40 veces a la api
            val doc = db.collection("top_five_books").document(search)

            try {
                val document = doc.get().await()
                if (document.exists()) {
                    //cargamos de firestore si ya existe
                    val topBooks = document.get("books") as? List<Map<String, Any>>?:emptyList()
                    _topFiveBooks.value = topBooks
                    println("TopFive: no hemos llamado a la api, hemos usado firestore.")
                }else{
                    delay(300L)
                    val response = RetrofitModule.api.searchBooks(search, key)
                    val items = response.items?.mapNotNull { item ->
                        val volumeInfo = item.volumeInfo
                        val ratingAverage = volumeInfo?.averageRating
                        //si tiene puntuacion
                        if(ratingAverage != null) {
                            mapOf(
                                "id" to (item.id ?: ""), //id para ir a BookDetailScreen
                                "thumbnail" to (volumeInfo.imageLinks?.thumbnail ?: ""),
                                "categories" to (volumeInfo.categories ?: listOf<String>()),
                                "rating" to ratingAverage.toDouble(),
                            )
                        }else null
                    }
                        ?.sortedByDescending { it["rating"] as Double }
                        ?.take(5)

                    val itemsBooks = items?:emptyList()
                    _topFiveBooks.value = itemsBooks

                    doc.set(mapOf("books" to itemsBooks))
                    println("TopFive: acabamos de usar la api, google no me bloquees.")
                }

            }catch (e:Exception){
                println("Exception HomeScreenViewModel: $e")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getRandomBooks(){

        _isLoading.value = true

        //buscar en api 10 libros random a partir de pillar 10 géneros random de la lista.
        viewModelScope.launch {
            //vamos a guardarlo en firestore para no llamar 40 veces a la api
            val doc = db.collection("random_books").document("random_books_data")
            try{
                val document = doc.get().await()

                if(document.exists()){ //buscamos si ya hay libros random en firestore
                    val docBooks = document.get("books")  as? List<Map<String, Any>>
                    _randomBooks.value = docBooks ?:emptyList()
                    println("Random: datos recogidos de firestore.")
                }else{
                    val bookListRandom = mutableListOf<Map<String, Any>>()
                    val genreList = genreBooksList().shuffled().take(5) //lista de géneros

                    for(genre in genreList){
                        try{
                            val response = RetrofitModule.api.searchBooks(genre,key)
                            val randomBooks = response.items?.filter {
                                val ratingBooks = it.volumeInfo?.averageRating
                                it.volumeInfo?.imageLinks?.thumbnail != null && ratingBooks != null && ratingBooks > 0
                            }

                            if(!randomBooks.isNullOrEmpty()){ //pillo uno random de aqui
                                val randomBook = randomBooks.random()
                                val volumeInfo = randomBook.volumeInfo

                                val bookRandomData = mapOf(
                                    "id" to (randomBook.id ?: ""),
                                    "thumbnail" to (volumeInfo?.imageLinks?.thumbnail ?: ""),
                                    "rating" to (volumeInfo?.averageRating ?: 0.0),
                                )

                                bookListRandom.add(bookRandomData)
                                println("Random: acabamos de usar la api, google no me bloquees.")
                            }

                            delay(1000L) //tiempo de espera para que google no se crea que le atacamos

                        }catch (e:Exception){
                            println("Exception HomeScreenViewModel: $e")
                        }
                    }
                    doc.set(
                        mapOf("books" to bookListRandom)).await() //guardar en firestore si no estaba ya (con fecha actual)
                    _randomBooks.value = bookListRandom
                }
            }catch (e:Exception){
                println("Exception HomeScreenViewModel: $e")
                _isLoading.value = false
            }
        }

    }
}

//    private var randomBooksLoaded = false
//
//    fun getRandomBooksSimple() {
//        if (randomBooksLoaded) return //evita volver a llamar
//
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = RetrofitModule.api.searchBooks("subject:fiction", key)
//                val books = response.items?.shuffled()?.take(10)?.map {
//                    mapOf(
//                        "id" to (it.id ?: ""),
//                        "thumbnail" to (it.volumeInfo?.imageLinks?.thumbnail ?: ""),
//                        "rating" to (it.volumeInfo?.averageRating ?: 0.0)
//                    )
//                } ?: emptyList()
//
//                _randomBooks.value = books
//                randomBooksLoaded = true
//            } catch (e: Exception) {
//                println("Error: $e")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}