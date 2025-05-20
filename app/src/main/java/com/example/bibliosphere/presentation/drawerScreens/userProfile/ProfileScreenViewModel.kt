package com.example.bibliosphere.presentation.drawerScreens.userProfile

import androidx.lifecycle.ViewModel
import com.example.bibliosphere.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileScreenViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    private val _imageResId = MutableStateFlow(R.drawable.profile_alien) //cargar de firestore
    val imageResId: StateFlow<Int> = _imageResId

    //para cambiar la imagen de perfil, solo para seleccionar
    fun setImageResId(resId: Int) {
        _imageResId.value = resId
    }

    //llamar en botón, se guarda en firestore en users
    fun updateImageResId(resId: Int) {
        //meter lo de firestore aqui
        db.collection("users").document(userId).update("image", resId) //actualizamos la imagen
    }

}