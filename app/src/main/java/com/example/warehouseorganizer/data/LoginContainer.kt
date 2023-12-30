package com.example.warehouseorganizer.data

import com.google.firebase.auth.FirebaseAuth

interface AppContainer {
    val loginRepository: LoginRepository
}

class LoginContainer : AppContainer {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override val loginRepository: LoginRepository by lazy {
        FirebaseAuthRepository(firebaseAuth)
    }
}