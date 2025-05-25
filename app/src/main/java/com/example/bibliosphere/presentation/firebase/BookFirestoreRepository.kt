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
                val book = api.getBookDetail(bookid) //esto ya no nos va a servir
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
    }

    //este es el q hay q usar
    suspend fun getBookDataFS(userId: String): List<BookData> { //recojo los datos de firestore que guardé desde saveBookStateFS
        return try{
            val doc = db.collection("users").document(userId).collection("library").get().await()
            doc.documents.map { documentSnapshot ->
                val bookData = documentSnapshot.data?: emptyMap<String,Any>()
                BookData(
                    id = documentSnapshot.id,
                    authors = bookData["authors"] as? List<String>?:emptyList(),
                    title = documentSnapshot["title"] as? String?:"",
                    publisher = documentSnapshot["publisher"] as? String?:"",
                    publishedDate = documentSnapshot["publishedDate"] as? String?:"",
                    thumbnail = documentSnapshot["thumbnail"] as? String?:"",
                    categories = documentSnapshot["categories"] as? List<String>?:emptyList(),
                )
            }
        }catch (e:Exception){
            emptyList()
        }
    }
}

data class BookData(
    val id: String,
    val authors: List<String>,
    val title: String,
    val publisher: String,
    val publishedDate: String,
    val thumbnail: String,
    val categories: List<String>,
)