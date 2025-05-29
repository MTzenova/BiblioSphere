package com.example.bibliosphere.presentation.home

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
            try {
                val response = RetrofitModule.api.searchBooks(search)
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

                _topFiveBooks.value = items

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
            val bookListRandom = mutableListOf<Map<String, Any>>()
            val genreList = genreBooksList().shuffled().take(5) //lista de géneros
             genreList.forEach { genre ->
                try{
                    val response = RetrofitModule.api.searchBooks(genre)
                    val randomBooks = response.items?.filter { it.volumeInfo?.imageLinks?.thumbnail != null }

                    if(!randomBooks.isNullOrEmpty()){ //pillo uno random de aqui
                        val randomBook = randomBooks.random()
                        val volumeInfo = randomBook.volumeInfo

                        bookListRandom.add(
                            mapOf(
                                "id" to (randomBook.id ?: ""),
                                "thumbnail" to (volumeInfo?.imageLinks?.thumbnail ?: ""),
                                "rating" to (volumeInfo?.averageRating ?: 0.0),
                            )
                        )
                    }

                }catch (e:Exception){
                    println("Exception HomeScreenViewModel: $e")
                }finally {
                    _isLoading.value = false
                }
            }
            //pillar un libro random de cada género. Devolver 10 libros.
            _randomBooks.value = bookListRandom
        }
    }
}