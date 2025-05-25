package com.example.bibliosphere.presentation.firebase

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.GoogleBooksApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BookFirestoreRepository(
    private val db: FirebaseFirestore,
    private val api:GoogleBooksApiService
) {

    suspend fun getUserLibraryId(userId:String):List<String> { //id de la library del usuario
        val document = db.collection("users").document(userId).collection("library").get().await()
        return document.documents.map { it.id } //revisar
    }

    suspend fun getUserBooks(userId:String): List<Item> { //recoge los libros del usuario

        val booksIds = getUserLibraryId(userId)
        return booksIds.mapNotNull { bookid ->
            try {
                val book = api.getBookDetail(bookid)
                book
            } catch (e: Exception) {
                println("Error al obtener libro con ID $bookid: ${e.message}")
                null
            }
        }
    }

    //para borarr libro de coleccion library
    suspend fun deleteUserBook(userId:String, bookId:String) {
        try{
            db.collection("users").document(userId).collection("library").document(bookId).delete().await()
            println("Successfully deleted user book $bookId")
        }catch (e:Exception){
            println("Error deleting user book $bookId")
        }
    }
}