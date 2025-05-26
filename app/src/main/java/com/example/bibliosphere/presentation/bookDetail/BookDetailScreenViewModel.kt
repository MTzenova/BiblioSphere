package com.example.bibliosphere.presentation.bookDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.R
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.components.textField.convertMillisToDate
import com.example.bibliosphere.presentation.firebase.BookFirestoreRepository
import com.example.bibliosphere.presentation.firebase.CommentData
import com.example.bibliosphere.presentation.firebase.UserFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
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

    private val repository = BookFirestoreRepository(
        db = FirebaseFirestore.getInstance(),
        api = RetrofitModule.api
    )

    private val userRepository = UserFirestoreRepository(db)

    private val _imageResId = MutableStateFlow(R.drawable.logo_sin_letras) //cargar de firestore
    val imageResId: StateFlow<Int> = _imageResId

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _comments = MutableStateFlow<List<CommentData>>(emptyList())
    val comments: StateFlow<List<CommentData>> = _comments

    fun getUserData(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val timeStamp = document.getTimestamp("birthDate")
                    val birthDateString = timeStamp?.toDate()?.time?.let { convertMillisToDate(it) } ?: ""

                    _userName.value = document.getString("userName")?: ""
                    // _userBirthday.value = document.getTimestamp("birthDate").toString()?: ""
                    _imageResId.value = (document.getLong("image")?.toInt()) ?: R.drawable.logo_sin_letras
                }

            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }

    }

    fun getUserImage(){
        viewModelScope.launch(Dispatchers.IO) {
            if (userId != null) {
                val image = userRepository.getUserImage(userId)
                if (image != null) {
                    _imageResId.value = image
                }
            }
        }
    }

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
        viewModelScope.launch {
            val book = _bookDetail.value
            if (book != null) {
                if (userId != null) {
                    repository.saveBookStateFS(bookId, states, userId, book)
                }
            }
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

    fun deleteBookFromLibrary(bookId: String) {
        viewModelScope.launch {
            userId.let { userId ->
                if (userId != null) {
                    repository.deleteUserBook(userId,bookId)
                }
            }
        }
    }

    //comentarios

    //enviar comentario a fs
    fun sendComment(bookId: String, commentText: String, userName: String, userImage: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        viewModelScope.launch {
            try{
                if (userId != null) {
                    repository.sendCommentFS(bookId,commentText,userId,userName,userImage)
                    val commentsLoaded = repository.getCommentsFS(bookId) //para recargar para ver el propio comentario
                    _comments.value = commentsLoaded
                }
            }catch(e:Exception){
                _errorMessage.value = e.localizedMessage
            }
        }
    }

    //recoger los comentarios de fs de un libro
    fun getComments(bookId: String) {

        viewModelScope.launch {
            try{
                val commentsLoaded = repository.getCommentsFS(bookId)
                _comments.value = commentsLoaded
            }catch(e:Exception){
                _errorMessage.value = e.localizedMessage
            }
        }
    }
}