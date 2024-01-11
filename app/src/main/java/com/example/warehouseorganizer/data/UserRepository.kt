package com.example.warehouseorganizer.data

import com.example.warehouseorganizer.model.User
import com.example.warehouseorganizer.ui.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface LoginRepository {
    suspend fun getCurrentUser(): User?
    suspend fun loginUser(email: String, password: String): LoginResult
    suspend fun logoutUser()
}

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) : LoginRepository {
    override suspend fun getCurrentUser(): User? {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        return currentUser?.let { User(it.uid, it.email ?: "") }
    }

    override suspend fun loginUser(email: String, password: String): LoginResult {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            firebaseUser?.let {
                LoginResult.Success(User(it.uid, it.email ?: ""))
            } ?: run {
                LoginResult.Error("User is null")
            }
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Login failed")
        }
    }

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }
}

interface SignUpRepository {
    suspend fun signUp(username: String, email: String, password: String): Result<Unit>
    suspend fun signUpWithGoogleIdToken(idToken: String): Result<Unit>
}

class FirebaseAuthSignUpRepository(private val firebaseAuth: FirebaseAuth) : SignUpRepository {
    override suspend fun signUp(username: String, email: String, password: String): Result<Unit> {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                    }
                }
        }
    }

    override suspend fun signUpWithGoogleIdToken(idToken: String): Result<Unit> = suspendCoroutine { continuation ->
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.success(Unit))
                } else {
                    continuation.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }
}