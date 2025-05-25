package com.example.bibliosphere.presentation.firebase

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
                println("Intentando obtener detalle del libro con ID: $bookid")
                val book = api.getBookDetail(bookid)
                println("Libro obtenido: ${book.volumeInfo?.title ?: "Sin título"}")
                book
            } catch (e: Exception) {
                println("Error al obtener libro con ID $bookid: ${e.message}")
                null
            }
        }
    }
}