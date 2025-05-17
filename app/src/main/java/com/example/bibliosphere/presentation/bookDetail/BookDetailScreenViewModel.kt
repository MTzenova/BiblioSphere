package com.example.bibliosphere.presentation.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

}