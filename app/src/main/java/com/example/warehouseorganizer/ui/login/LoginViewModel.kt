package com.example.warehouseorganizer.ui.login

import androidx.lifecycle.ViewModel
import com.example.warehouseorganizer.model.User
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.warehouseorganizer.data.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _emailState = mutableStateOf("")
    val emailState: MutableState<String> = _emailState

    private val _passwordState = mutableStateOf("")
    val passwordState: MutableState<String> = _passwordState

    private val _loginState: MutableStateFlow<LoginResult?> = MutableStateFlow(null)
    val loginState: StateFlow<LoginResult?> = _loginState.asStateFlow()

    fun onEmailChange(email: String) {
        _emailState.value = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }

    suspend fun loginUser() {
        val email = _emailState.value
        val password = _passwordState.value

        _loginState.value = LoginResult.Success(User("id", "dummy@gmail.com")) // Placeholder loading state

        // Panggil metode loginUser di LoginRepository
        _loginState.value = loginRepository.loginUser(email, password)
    }
}