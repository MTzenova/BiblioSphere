package com.example.bibliosphere.presentation.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterScreenViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _isValidUserName = MutableLiveData<Boolean>()
    val isValidUserName: LiveData<Boolean> = _isValidUserName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean> = _isValidEmail

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordVisible = MutableLiveData(false)
    val passwordVisible: LiveData<Boolean> = _passwordVisible

    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean> = _isValidPassword

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> = _birthDate

    private val _isValidBirthDate = MutableLiveData<Boolean>()
    val isValidBirthDate: LiveData<Boolean> = _isValidBirthDate

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable

    fun onRegisterChanged(username:String, email:String, password:String, birthDate: String){
        _userName.value = username
        _email.value = email
        _password.value = password
        _birthDate.value = birthDate
        _registerEnable.value = (isValidUserName(username) && isValidEmail(email) && isValidPassword(password) && isValidBirthDate(birthDate))
        _isValidPassword.value = isValidPassword(password)
        _isValidEmail.value = isValidEmail(email)
        _isValidUserName.value = isValidUserName(username)
        _isValidBirthDate.value = isValidBirthDate(birthDate)
    }

    private fun isValidUserName(userName: String): Boolean = userName.length in 4..14

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidBirthDate(birthDate: String): Boolean = birthDate.isNotEmpty()

    fun togglePasswordVisibility() {
        _passwordVisible.value = _passwordVisible.value != true
    }
}