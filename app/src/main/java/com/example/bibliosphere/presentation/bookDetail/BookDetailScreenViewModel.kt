package com.example.bibliosphere.presentation.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.GoogleBooksApiService
import com.example.bibliosphere.data.network.RetrofitModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailScreenViewModel : ViewModel() {
    private val _bookDetail = MutableStateFlow<Item?>(null)
    val bookDetail: StateFlow<Item?> = _bookDetail

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadBookDetail(bookId: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null
            try{
                val bookDetail = RetrofitModule.api.getBookDetail(bookId)
                _bookDetail.value = bookDetail
            }catch(e:Exception){
                _errorMessage.value = e.localizedMessage
            }finally {
                _loading.value = false
            }
        }
    }

}