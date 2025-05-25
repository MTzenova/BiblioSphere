package com.example.bibliosphere.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
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

    private val _books = MutableStateFlow <List<Item>>(emptyList())
    val books: StateFlow<List<Item>> = _books

    //cargamos los libros del usuario, pero deberiamos filtrar por libros con estado
    fun getUserBooks(userId: String) {

        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getUserBooks(userId)
                println("Cantidad de libros obtenidos: ${list.size}")
                _books.value = list
            }catch (e: Exception) {
                _error.value = e.message
            }finally {
                _isLoading.value = false
            }


        }
    }

}