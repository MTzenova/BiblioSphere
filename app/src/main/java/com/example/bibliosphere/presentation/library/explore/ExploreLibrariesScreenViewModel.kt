package com.example.bibliosphere.presentation.library.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosphere.presentation.firebase.UserData
import com.example.bibliosphere.presentation.firebase.UserFirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreLibrariesScreenViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _userData = MutableStateFlow<List<UserData>>(emptyList())
    val userData: StateFlow<List<UserData>> = _userData

    private val db = FirebaseFirestore.getInstance()

    private val repository = UserFirestoreRepository(db)

    fun getUserData() {
        viewModelScope.launch {
            _isLoading.value = true

            val ids = repository.getAllUsersId()

            val users = ids.mapNotNull { id ->
                val user = repository.getUserData(id)
                println("Usuario obtenido: $user")
                user
            }

            _userData.value = users
            _isLoading.value = false
        }
    }

}