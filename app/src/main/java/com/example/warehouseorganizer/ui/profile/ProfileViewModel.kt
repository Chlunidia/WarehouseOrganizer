package com.example.warehouseorganizer.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _usernameState: MutableStateFlow<String?> = MutableStateFlow(null)
    val usernameState: StateFlow<String?> = _usernameState.asStateFlow()

    private val _emailState: MutableStateFlow<String?> = MutableStateFlow(null)
    val emailState: StateFlow<String?> = _emailState.asStateFlow()

    private val _photoUrlState: MutableStateFlow<String?> = MutableStateFlow(null)
    val photoUrlState: StateFlow<String?> = _photoUrlState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            _usernameState.value = currentUser.displayName ?: "No Username"
            _emailState.value = currentUser.email ?: "No Email"
            _photoUrlState.value = currentUser.photoUrl.toString()
        }
    }
}
