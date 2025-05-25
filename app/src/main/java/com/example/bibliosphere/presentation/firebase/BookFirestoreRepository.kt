package com.example.bibliosphere.presentation.firebase

import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.data.network.GoogleBooksApiService
import com.example.bibliosphere.presentation.components.buttons.BookState
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
                val book = api.getBookDetail(bookid) //esto para que es
                book
            } catch (e: Exception) {
                println("Error al obtener libro con ID $bookid: ${e.message}")
                null
            }
        }
    }

    //para borrar libro de coleccion library
    suspend fun deleteUserBook(userId:String, bookId:String) {
        try{
            db.collection("users").document(userId).collection("library").document(bookId).delete().await()
            println("Successfully deleted user book $bookId")
        }catch (e:Exception){
            println("Error deleting user book $bookId")
        }
    }

    suspend fun saveBookStateFS(bookId: String, states: Set<BookState>, userId:String, book:Item?) {

        val doc = db.collection("users").document(userId).collection("library").document(bookId)

        try{
            val document = doc.get().await()

            if(document.exists()) { //si ya guardó dantos anteriormente, solo cambia el estado
                doc.update("status", states.map { it.name }).await()
            }else{
                val bookData = mutableMapOf<String, Any>("status" to states.map{it.name})
                "status" to states.map{it.name}

                book?.volumeInfo?.let { volInfo ->
                    bookData["authors"] = volInfo.authors?: emptyList<String>()
                    bookData["title"] = volInfo.title?: ""
                    bookData["publisher"] = volInfo.publisher?: ""
                    bookData["publishedDate"] = volInfo.publishedDate?: ""
                    bookData["thumbnail"] = volInfo.imageLinks?.thumbnail?:""
                    bookData["categories"] = volInfo.categories ?: emptyList<String>()
                    //si necesito mas datos, los puedo poner aqui
                }
                doc.set(bookData).await()
            }

        }catch(e:Exception){
            println("Error al obtener libro con ID $bookId: ${e.message}")
        }
//        val bookData = mutableMapOf<String, Any>(
//            "status" to states.map{it.name}
//        )
//
//        book?.volumeInfo?.let { volInfo ->
//            bookData["authors"] = volInfo.authors?: emptyList<String>()
//            bookData["title"] = volInfo.title?: ""
//            bookData["publisher"] = volInfo.publisher?: ""
//            bookData["publishedDate"] = volInfo.publishedDate?: ""
//            bookData["thumbnail"] = volInfo.imageLinks?.thumbnail?:""
//            bookData["categories"] = volInfo.categories ?: emptyList<String>()
//            //si necesito mas datos, los puedo poner aqui
//        }
//
//        try {
//            db.collection("users").document(userId)
//                .collection("library")
//                .document(bookId)
//                .set(bookData)
//                .await()
//            println("Successfully updated user book $bookId")
//        }catch (e:Exception){
//            println("Error saving state book $bookId")
//        }

    }

}