package com.example.bibliosphere.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.BookUI
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.bookState.BookState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _books = MutableStateFlow<List<Item>>(emptyList())
    val books: StateFlow<List<Item>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error



    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun searchBooks() {
        if (_query.value.isBlank()) return

        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitModule.api.searchBooks(_query.value)
                _books.value = response.items ?: emptyList()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _booksState = MutableStateFlow<List<BookUI>>(emptyList())
    val booksState: StateFlow<List<BookUI>> = _booksState

    fun updateBookState(newStates: Set<BookState>, bookId: String) {
        _booksState.update { list ->
            list.map{ book ->
                if( book.id == bookId){
                    book.copy(states = newStates)
                }else{
                    book
                }

            }

        }
    }
}