package com.example.bibliosphere.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.firebase.BookData
import com.example.bibliosphere.presentation.firebase.BookFirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch


class MyLibraryScreenViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val repository = BookFirestoreRepository(
        db = FirebaseFirestore.getInstance(),
        api = RetrofitModule.api
    )

    private val _books = MutableStateFlow <List<BookData>>(emptyList())
    val books: StateFlow<List<BookData>> = _books

    //cargamos los libros del usuario, pero deberiamos filtrar por libros con estado


    fun getUserBooks(userId: String) {

        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getBookDataFS(userId)
                _books.value = list
            }catch (e: Exception) {
                _error.value = e.message
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun filterStatusBooks(status: BookState?, userId: String) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getStatusBookFS(userId)
                val books = if (status == null) {
                    list.map { it.bookId }
                }else{
                    list.filter { it.status.contains(status.name) }.map { it.bookId }
                }

                val booksFiltered = _books.value.filter { book -> books.contains(book.id) }

                _books.value = booksFiltered

            }catch (e: Exception) {
                _error.value = e.message
            }finally {
                _isLoading.value = false
            }
        }
    }

//    fun getUserBooks(userId: String) {
//
//        _isLoading.value = true
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val list = repository.getUserBooks(userId)
//                _books.value = list
//            }catch (e: Exception) {
//                _error.value = e.message
//            }finally {
//                _isLoading.value = false
//            }
//
//
//        }
//    }

}