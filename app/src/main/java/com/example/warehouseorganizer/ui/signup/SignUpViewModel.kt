package com.example.warehouseorganizer.ui.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.warehouseorganizer.data.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val errorMessage: String) : SignUpState()
}

class SignUpViewModel() : ViewModel() {

    private val _usernameState = MutableStateFlow("")
    val usernameState: StateFlow<String> = _usernameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun onUsernameChange(username: String) {
        _usernameState.value = username
    }

    fun onEmailChange(email: String) {
        _emailState.value = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }

    fun signUpUser(onSignUpSuccess: () -> Unit) {
        val username = _usernameState.value
        val email = _emailState.value
        val password = _passwordState.value

        if (validateInputs(username, email, password)) {
            _signUpState.value = SignUpState.Loading
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())
                            ?.addOnCompleteListener { updateProfileTask ->
                                if (updateProfileTask.isSuccessful) {
                                    _signUpState.value = SignUpState.Success
                                    onSignUpSuccess()
                                } else {
                                    _signUpState.value = SignUpState.Error("Failed to update display name")
                                }
                            }
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthException) {
                            _signUpState.value = SignUpState.Error(exception.message ?: "Sign-up failed")
                        } else {
                            _signUpState.value = SignUpState.Error("Sign-up failed")
                        }
                    }
                }
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        return when {
            username.isBlank() -> {
                _signUpState.value = SignUpState.Error("Username cannot be empty")
                false
            }
            email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _signUpState.value = SignUpState.Error("Invalid email address")
                false
            }
            password.length < 6 -> {
                _signUpState.value = SignUpState.Error("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }
}
