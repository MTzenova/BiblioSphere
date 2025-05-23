package com.example.bibliosphere.presentation.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.BookUI
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.BookState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailScreenViewModel : ViewModel() {
    private val _bookDetail = MutableStateFlow<Item?>(null) //el libro que se va a mostrar
    val bookDetail: StateFlow<Item?> = _bookDetail

    private val _loading = MutableStateFlow(false) //si está cargando
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null) //si hay error
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadBookDetail(bookId: String) { //carga libro según la id que se le pase
        viewModelScope.launch {
            _loading.value = true //carga
            _errorMessage.value = null //se limpian errores
            try{
                val bookDetail = RetrofitModule.api.getBookDetail(bookId) //hace la consulta a la api para conseguir el libro según la id pasada
                _bookDetail.value = bookDetail //guardamos el libro
            }catch(e:Exception){
                _errorMessage.value = e.localizedMessage
            }finally {
                _loading.value = false //termina carga
            }
        }
    }

    //estado del libro
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