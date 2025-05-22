package com.example.bibliosphere.presentation.components.bookState

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookStateViewModel : ViewModel() {

//    //variables de estado para los libros
//    private val _favorite = MutableStateFlow(false)
//    val favorite: StateFlow<Boolean> = _favorite
//
//    private val _reading = MutableStateFlow(false)
//    val reading: StateFlow<Boolean> = _reading
//
//    private val _readed = MutableStateFlow(false)
//    val readed: StateFlow<Boolean> = _readed
//
//    private val _pending = MutableStateFlow(false)
//    val pending: StateFlow<Boolean> = _pending
    private val _bookStates = MutableStateFlow<Set<BookState>>(emptySet())
    val bookStates: StateFlow<Set<BookState>> = _bookStates

    fun setBookState(states: Set<BookState>) {
        _bookStates.value = states
    }

    fun toggleBookState(state: BookState) {
        val bookState = _bookStates.value.toMutableSet()
        if(state in bookState) {
            bookState.remove(state)
        }else{
            if(state != BookState.FAVORITO) { //si no es favorito, al marcar uno, desmarca los demas que no son favoritos
                bookState.removeAll(setOf(BookState.PENDIENTE, BookState.LEYENDO, BookState.LEIDO))
            }
            bookState.add(state)
        }
        _bookStates.value = bookState
    }


    //funciones para marcar/desmarcar estado del libro
//    fun markFavorite() {
//        _favorite.value = !_favorite.value
//    }
//
//    fun markReading() {
//        _reading.value = true
//        _readed.value = false
//        _pending.value = false
//    }
//
//    fun markReaded() {
//        _readed.value = true
//        _reading.value = false
//        _pending.value = false
//    }
//
//    fun markPending() {
//        _pending.value = true
//        _reading.value = false
//        _readed.value = false
//    }
}