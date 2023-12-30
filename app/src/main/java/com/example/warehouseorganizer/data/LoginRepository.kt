package com.example.warehouseorganizer.data

import com.example.warehouseorganizer.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

interface LoginRepository {
    suspend fun getCurrentUser(): User?
    suspend fun loginUser(email: String, password: String): User?
    suspend fun logoutUser()
}

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) : LoginRepository {
    override suspend fun getCurrentUser(): User? {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        return currentUser?.let { User(it.uid, it.email ?: "") }
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            firebaseUser?.let { User(it.uid, it.email ?: "") }
        } catch (e: Exception) {
            null // Handle login error more appropriately if needed
        }
    }

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }
}