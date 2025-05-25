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

    suspend fun getUserLibrary(userId:String):List<Map<String, Any>>{ //obtenemos la bilioteca de un usuario y la guardamos en la lista

        return try{
            val doc = db.collection("users").document(userId).collection("library").get().await()
            doc.documents.mapNotNull { it.data }
        }catch ( e:Exception){
            emptyList()
        }

    } //al igual no hace ni falta esto

}