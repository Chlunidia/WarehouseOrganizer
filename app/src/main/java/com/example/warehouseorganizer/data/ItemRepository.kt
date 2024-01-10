package com.example.warehouseorganizer.data

import com.example.warehouseorganizer.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface ItemRepository {
    fun getAllItems(): Flow<List<Item>>
    suspend fun saveItem(item: Item): String
    suspend fun updateItem(item: Item)
    suspend fun deleteItem(itemId: String)
    fun getItemById(itemId: String): Flow<Item>
}

class FirebaseItemRepository(private val firestore: FirebaseFirestore) : ItemRepository {

    override fun getAllItems(): Flow<List<Item>> = flow {
        val snapshot = firestore.collection("items")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .await()
        val items = snapshot.toObjects(Item::class.java)
        emit(items)
    }.flowOn(Dispatchers.IO)

    override suspend fun saveItem(item: Item): String {
        return try {
            val documentReference = firestore.collection("items").add(item).await()
            // Update the Item with the Firestore-generated DocumentReference
            firestore.collection("items").document(documentReference.id)
                .set(item.copy(id = documentReference.id))
            "Berhasil + ${documentReference.id}"
        } catch (e: FirebaseFirestoreException) {
            "Gagal $e"
        }
    }

    override suspend fun updateItem(item: Item) {
        firestore.collection("items").document(item.id).set(item).await()
    }

    override suspend fun deleteItem(itemId: String) {
        firestore.collection("items").document(itemId).delete().await()
    }

    override fun getItemById(itemId: String): Flow<Item> = flow {
        val snapshot = firestore.collection("items").document(itemId).get().await()
        val item = snapshot.toObject(Item::class.java)
        emit(item!!)
    }.flowOn(Dispatchers.IO)
}
