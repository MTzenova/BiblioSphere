package com.example.bibliosphere.presentation.drawerScreens.userProfile

import androidx.lifecycle.ViewModel
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.components.convertMillisToDate
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import java.text.SimpleDateFormat
import java.util.*

class ProfileScreenViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    private val _imageResId = MutableStateFlow(R.drawable.logo_sin_letras) //cargar de firestore
    val imageResId: StateFlow<Int> = _imageResId

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("")
    val userPassword: StateFlow<String> = _userPassword

    private val _userBirthday = MutableStateFlow("")
    val userBirthday: StateFlow<String> = _userBirthday


    init{
        if(currentUser != null){
            getUserData(userId)
        }
    }

    //para cambiar la imagen de perfil, solo para seleccionar
    fun setImageResId(resId: Int) {
        _imageResId.value = resId
    }

    //llamar en botón, se guarda en firestore en users
    fun updateImageResId(resId: Int) {
        //meter lo de firestore aqui
        db.collection("users").document(userId).update("image", resId) //actualizamos la imagen
    }

    fun getUserData(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
            if (document.exists()) {
                val timeStamp = document.getTimestamp("birthDate")
                val birthDateString = timeStamp?.toDate()?.time?.let { convertMillisToDate(it) } ?: ""

                _userBirthday.value = birthDateString
                _userName.value = document.getString("userName")?: ""
                _userEmail.value = document.getString("email")?: ""
               // _userBirthday.value = document.getTimestamp("birthDate").toString()?: ""
                _imageResId.value = (document.getLong("image")?.toInt()) ?: R.drawable.logo_sin_letras
            }

        }.addOnFailureListener { exception ->
            exception.printStackTrace()
            }

    }

    fun updateProfileData(userName: String, userBirthday:String){
        db.collection("users").document(userId).update("userName", userName)

        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = simpleDateFormat.parse(userBirthday)
        val userBirthdayTs = Timestamp(date!!)
        db.collection("users").document(userId).update("birthDate", userBirthdayTs)

        getUserData(userId)
    }

}