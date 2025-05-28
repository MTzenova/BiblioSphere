package com.example.bibliosphere.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.firebase.BookFirestoreRepository
import com.example.bibliosphere.presentation.firebase.BookStatusFS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel(){

    private val db = FirebaseFirestore.getInstance()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val bookFirestoreRepository = BookFirestoreRepository(db = FirebaseFirestore.getInstance(),
        api = RetrofitModule.api)

    private val _bookStatusFS = MutableLiveData<List<BookStatusFS>>()
    val bookStatusFS: LiveData<List<BookStatusFS>> = _bookStatusFS

    private val _topFiveBooks = MutableLiveData<List<Map<String, Any>>>()
    val topFiveBooks: LiveData<List<Map<String, Any>>> = _topFiveBooks

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
            }
        }


    }

}