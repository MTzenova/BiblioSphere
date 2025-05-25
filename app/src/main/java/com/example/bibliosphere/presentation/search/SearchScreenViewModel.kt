package com.example.bibliosphere.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.BookUI
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.firebase.BookFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SearchScreenViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _books = MutableStateFlow<List<Item>>(emptyList())
    val books: StateFlow<List<Item>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _booksState = MutableStateFlow<List<BookUI>>(emptyList())
    val booksState: StateFlow<List<BookUI>> = _booksState

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    private val repository = BookFirestoreRepository(
        db = FirebaseFirestore.getInstance(),
        api = RetrofitModule.api
    )

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
                val items =  response.items ?: emptyList()
                _books.value = items

                val firestoreStates = getBooksStatesFS()

                _booksState.value = items.mapNotNull { item ->
                    item.id?.let{ bookId ->
                        val statesSaved = firestoreStates[bookId]?: emptySet()
                        BookUI(id = bookId, states = statesSaved)
                    }

                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateBookState(newStates: Set<BookState>, bookId: String) {
//        _booksState.update { list ->
//            list.map{ book ->
//                if( book.id == bookId){
//                    book.copy(states = newStates)
//                }else{
//                    book
//                }
//
//            }
//
//        }
        _booksState.value = _booksState.value.map { bookUI ->
            if (bookUI.id == bookId) {
                bookUI.copy(states = newStates)
            } else {
                bookUI
            }
        }
        //probando si funciona el guardar en firestore (funciona)
        saveBookStateFS(bookId, newStates)
    }

    private fun saveBookStateFS(bookId: String, states: Set<BookState>) {
        //tuve que poner permisos también
        val bookData = mapOf("status" to states.map { it.name })
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("library")
                .document(bookId)
                .set(bookData)
                .addOnSuccessListener {
                    println("Successfully updated book $bookData")
                }
        }
    }

    private suspend fun getBooksStatesFS(): Map<String, Set<BookState>> {
        if (userId == null) return emptyMap()

        return try {
            val documents = db.collection("users")
                .document(userId)
                .collection("library")
                .get()
                .await()

            documents.associate { document ->
                val idBookFS = document.id
                val status = document.get("status") as? List<*>
                val statusNames = status?.mapNotNull { name ->
                    try {
                        BookState.valueOf(name.toString())
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                }?.toSet() ?: emptySet()
                idBookFS to statusNames
            }
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            emptyMap()
        }
    }

    fun deleteBookFromLibrary(bookId: String) {
        viewModelScope.launch {
            userId.let { userId ->
                if (userId != null) {
                    repository.deleteUserBook(userId,bookId)
                    //searchBooks()
                }
            }
        }
    }

}