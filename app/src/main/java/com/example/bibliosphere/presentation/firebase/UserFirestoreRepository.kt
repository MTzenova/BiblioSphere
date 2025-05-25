package com.example.bibliosphere.presentation.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserFirestoreRepository( private val db: FirebaseFirestore) {

    suspend fun getAllUsersId():List<String> { //obtenemos las ids de todos los usuarios
        return try{

            val doc = db.collection("users").get().await()
            doc.documents.map { it.id }

        }catch (e:Exception){
            emptyList()
        }
    }

    suspend fun getUserData(userId:String): UserData?{
        return try{
            val doc = db.collection("users").document(userId).get().await()

            if(!doc.exists()) return null

            val idUser = doc.id
            val userName = doc.getString("userName")?:""
            val email = doc.getString("email")?:""
            val profileImage = doc.getLong("image")?.toInt() ?: 0
            val library = db.collection("users").document(userId).collection("library").get().await()
            val booksNumber = library.size()

            UserData(
                userId = idUser,
                userName = userName,
                email = email,
                profileImage = profileImage,
                booksNumber = booksNumber
            )
        }catch (e:Exception){
            null
        }

    }

    suspend fun getUserLibrary(userId:String):List<Map<String, Any>>{ //obtenemos la bilioteca de un usuario y la guardamos en la lista

        return try{
            val doc = db.collection("users").document(userId).collection("library").get().await()
            doc.documents.mapNotNull { it.data }
        }catch ( e:Exception){
            emptyList()
        }

    } //al igual no hace ni falta esto

}

data class UserData(
    val userId: String,
    val userName: String,
    val email: String,
    val profileImage: Int?,
    val booksNumber: Int
)