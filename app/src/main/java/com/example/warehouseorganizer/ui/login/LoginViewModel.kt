package com.example.warehouseorganizer.ui.login

import androidx.lifecycle.ViewModel
import com.example.warehouseorganizer.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LoginState {
    object Loading : LoginState()
    class Success(val user: User) : LoginState()
    class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    // State untuk email dan password
    private val _emailState = mutableStateOf("")
    val emailState: MutableState<String> = _emailState

    private val _passwordState = mutableStateOf("")
    val passwordState: MutableState<String> = _passwordState

    // State untuk status login
    private val _loginState: MutableStateFlow<LoginState?> = MutableStateFlow(null)
    val loginState: StateFlow<LoginState?> = _loginState.asStateFlow()

    // Metode untuk merubah nilai email dan password
    fun onEmailChange(email: String) {
        _emailState.value = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }

    // Metode untuk melakukan login
    suspend fun loginUser() {
        val email = _emailState.value
        val password = _passwordState.value

        _loginState.value = LoginState.Loading

        // Panggil metode loginUserWithEmailPassword pada LoginViewModel
        loginUserWithEmailPassword(email, password)
    }

    // Metode untuk melakukan login menggunakan Firebase
    private suspend fun loginUserWithEmailPassword(email: String, password: String) {
        try {
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = authResult.user

            // Jika login berhasil, update _loginState dengan LoginState.Success
            firebaseUser?.let {
                _loginState.value = LoginState.Success(User(it.uid, it.email ?: ""))
            } ?: run {
                _loginState.value = LoginState.Error("User is null")
            }
        } catch (e: Exception) {
            // Jika login gagal, update _loginState dengan LoginState.Error
            _loginState.value = LoginState.Error(e.message ?: "Login failed")
        }
    }
}