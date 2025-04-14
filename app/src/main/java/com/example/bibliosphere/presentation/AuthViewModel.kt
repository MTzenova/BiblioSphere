package com.example.bibliosphere.presentation

import android.app.Activity
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

class AuthViewModel: ViewModel()  {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init{
        checkAuthStatus()
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
                _authState.value = AuthState.Error(task.exception?.message?:"Parece que algo fue mal")
            }
        }

    }

    fun createAccountWithEmail(email: String, password: String) {

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Parece que algo fue mal")
                }
            }

    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


// Añade esta función a tu AuthViewModel

    suspend fun signInWithGoogle(context: Activity) {
        try {
            _authState.value = AuthState.Loading

            // Crear el credentialManager
            val credentialManager = CredentialManager.create(context)

            // Configurar la solicitud para Google Sign-In
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setNonce(getNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // Obtener la credencial
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                try {
                    // Parsear la credencial de Google
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                    // Crear credencial para Firebase
                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

                    // Autenticar con Firebase
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _authState.value = AuthState.Authenticated
                            } else {
                                _authState.value = AuthState.Error(task.exception?.message ?: "Error al autenticar con Google")
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
    data class Error(val message: String) : AuthState()
}

