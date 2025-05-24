package com.example.bibliosphere.presentation.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BookDetailScreenViewModel : ViewModel() {
    private val _bookDetail = MutableStateFlow<Item?>(null) //el libro que se va a mostrar
    val bookDetail: StateFlow<Item?> = _bookDetail

    private val _loading = MutableStateFlow(false) //si está cargando
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null) //si hay error
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _booksState =  MutableStateFlow<Set<BookState>>(emptySet())
    val booksState: StateFlow<Set<BookState>> = _booksState

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadBookDetail(bookId: String) { //carga libro según la id que se le pase
        viewModelScope.launch {
            _loading.value = true //carga
            _errorMessage.value = null //se limpian errores
            try{
                val bookDetail = RetrofitModule.api.getBookDetail(bookId) //hace la consulta a la api para conseguir el libro según la id pasada
                _bookDetail.value = bookDetail //guardamos el libro

                val states = getBooksStatesFS(bookId)
                _booksState.value = states

            }catch(e:Exception){
                _errorMessage.value = e.localizedMessage
            }finally {
                _loading.value = false //termina carga
            }
        }
    }

    fun updateBookState(newStates: Set<BookState>, bookId: String) {
        _booksState.value = newStates
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
        }else{
            println("User not logged in")
        }
    }

    private suspend fun getBooksStatesFS(bookId: String): Set<BookState> {
        if (userId == null) return emptySet()

        return try {
            val documents = db.collection("users")
                .document(userId)
                .collection("library")
                .document(bookId)
                .get()
                .await()

            if(documents.exists())  {

                val status = documents.get("status") as? List<*>
                status?.mapNotNull { name ->
                    try {
                        BookState.valueOf(name.toString())
                    } catch (e: IllegalArgumentException) {
                        println("Error, estado no válido")
                        null
                    }
                }?.toSet() ?: emptySet()

            }else{
                emptySet()
            }
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            emptySet()
        }
    }
}