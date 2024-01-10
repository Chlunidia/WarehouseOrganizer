package com.example.warehouseorganizer.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val itemRepository: ItemRepository
    val loginRepository: LoginRepository
    val signUpRepository: SignUpRepository
}

class WarehouseOrganizerContainer : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override val itemRepository: ItemRepository by lazy {
        FirebaseItemRepository(firestore)
    }

    override val loginRepository: LoginRepository by lazy {
        FirebaseAuthRepository(firebaseAuth)
    }

    override val signUpRepository: SignUpRepository by lazy {
        FirebaseAuthSignUpRepository(firebaseAuth)
    }
}