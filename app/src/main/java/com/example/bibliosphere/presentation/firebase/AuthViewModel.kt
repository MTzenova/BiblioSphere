package com.example.bibliosphere.presentation.firebase

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bibliosphere.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.security.MessageDigest
import java.util.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale


class AuthViewModel: ViewModel()  {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init{
        checkAuthStatus()
        auth.addAuthStateListener { authState ->
            val user = authState.currentUser
            _userName.value = user?.displayName ?:""
        }
    }

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {task->
            if(task.isSuccessful){
                _authState.value = AuthState.Authenticated
            }else{
                _authState.value = AuthState.Error(task.exception?.message ?: "Parece que algo fue mal")
            }
        }

    }

    fun String.toTimestampOrNull(pattern: String = "MM/dd/yyyy"): Timestamp? {
        if (this.isBlank()) return null
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.US)
            val date = sdf.parse(this)
            date?.let { Timestamp(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun createAccountWithEmail(email: String, password: String, userName: String, birthDate: String) {

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->

                val userId = auth.currentUser?.uid
                val birthDateTimestamp = birthDate.toTimestampOrNull() //convertir fecha para firestore
                val image = R.drawable.logo_sin_letras //para añadir una imagen por defecto al crear el usuario

                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated

                    if(userId != null){
                        val users = hashMapOf(
                            "userName" to userName,
                            "birthDate" to birthDateTimestamp,
                            "email" to email,
                            "image" to image
                        )
                        val profile = UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                        
                        auth.currentUser?.updateProfile(profile)
                            ?.addOnFailureListener { e ->
                                Log.e("Auth", "No se pudo actualizar displayName", e)
                            }
                        val db = FirebaseFirestore.getInstance()
                        db.collection("users").document(userId)
                            .set(users)
                            .addOnSuccessListener {
                                println("Usuario guardado correctamente en la bbdd.")
                            }
                            .addOnFailureListener {
                                println("ERROR al intentar guardar el usuario en la bbdd.")
                                _authState.value = AuthState.Error(it.message.toString())
                            }
                    }

                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Parece que algo fue mal")
                }
            }

    }

    fun resetPassword(email: String){
        _authState.value = AuthState.Loading
        auth.useAppLanguage()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Email enviado.")
                    _authState.value = AuthState.PasswordResetEmailSent
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


    suspend fun signInWithGoogle(context: Activity) {
        try {
            _authState.value = AuthState.Loading

            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setNonce(getNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _authState.value = AuthState.Authenticated
                            } else {
                                _authState.value =
                                    AuthState.Error(task.exception?.message ?: "Error al autenticar con Google")
                            }
                        }
                } catch (e: GoogleIdTokenParsingException) {
                    _authState.value = AuthState.Error("Error al procesar el token de Google: ${e.message}")
                }
            } else {
                _authState.value = AuthState.Error("No se pudo obtener credencial de Google")
            }

        } catch (e: Exception) {
            _authState.value = AuthState.Error("Error en la autenticación con Google: ${e.message}")
        }
    }

    // Función auxiliar para generar un nonce aleatorio
    private fun getNonce(): String {
//        val randomBytes = ByteArray(32)
//        val random = Random()
//        random.nextBytes(randomBytes)
//        val digest = MessageDigest.getInstance("SHA-256")
//        val hash = digest.digest(randomBytes)
//        return android.util.Base64.encodeToString(hash, android.util.Base64.NO_WRAP)
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object PasswordResetEmailSent : AuthState()
    data class Error(val message: String) : AuthState()
}

