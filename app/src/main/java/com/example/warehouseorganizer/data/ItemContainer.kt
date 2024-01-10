package com.example.warehouseorganizer.data

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

interface AppContainer {
    val itemRepository: ItemRepository
    val loginRepository: LoginRepository
    val signUpRepository: SignUpRepository
}

class WarehouseOrganizerContainer(private val context: Context) : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    override val itemRepository: ItemRepository by lazy {
        FirebaseItemRepository(firestore, storage, context)
    }

    override val loginRepository: LoginRepository by lazy {
        FirebaseAuthRepository(firebaseAuth)
    }

    override val signUpRepository: SignUpRepository by lazy {
        FirebaseAuthSignUpRepository(firebaseAuth)
    }
}