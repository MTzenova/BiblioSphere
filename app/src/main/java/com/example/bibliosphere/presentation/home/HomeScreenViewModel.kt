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

    fun getStatusBooks() {

        userId?.let {  id ->
            viewModelScope.launch {
                val status = bookFirestoreRepository.getStatusBookFS(id)
                _bookStatusFS.value = status
               Log.d("HomeScreenViewModel", "Datos obtenidos: $status")
            }
        }
    }
}