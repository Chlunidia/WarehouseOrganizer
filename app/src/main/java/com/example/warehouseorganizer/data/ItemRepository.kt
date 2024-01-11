package com.example.warehouseorganizer.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.warehouseorganizer.model.Item
import com.example.warehouseorganizer.ui.add.AddEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.IOException

interface ItemRepository {
    fun getAllItems(): Flow<List<Item>>
    suspend fun saveItem(item: Item, bitmap: Bitmap? = null): String
    suspend fun updateItem(item: Item, bitmap: Bitmap? = null)
    suspend fun deleteItem(itemId: String)
    fun getItemById(itemId: String): Flow<Item>
    suspend fun saveItemWithImage(bitmap: Bitmap, name: String, rack: String, quantity: Int): String
    suspend fun updateItemWithImage(item: Item)
    suspend fun saveUploadImg(
        bitmap: Bitmap,
        name: String,
        quantity: Int,
        rack: String
    ): String
    fun getBitmapFromUri(uri: Uri): Bitmap?
}

class FirebaseItemRepository(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val context: Context
) : ItemRepository {
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid.toString()
    override fun getAllItems(): Flow<List<Item>> = flow {
        val snapshot = firestore.collection("users").document(uid)
            .collection("items")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .await()

        val items = snapshot.toObjects(Item::class.java)
        emit(items)
    }.flowOn(Dispatchers.IO)

    override suspend fun saveItem(item: Item, bitmap: Bitmap?): String {
        return try {
            val documentReference = firestore.collection("users").document(uid)
                .collection("items").add(item).await()

            val updatedItem = item.copy(id = documentReference.id)

            firestore.collection("users").document(uid)
                .collection("items").document(documentReference.id)
                .set(updatedItem)

            "Berhasil + ${documentReference.id}"
        } catch (e: Exception) {
            "Gagal $e"
        }
    }

    override suspend fun saveItemWithImage(bitmap: Bitmap, name: String, rack: String, quantity: Int): String {
        return try {
            val imageUrl = saveUploadImg(bitmap, name, quantity, rack)
            val item = Item(imageUrl = imageUrl, name = name, rack = rack, quantity = quantity)

            val documentReference = firestore.collection("users").document(uid)
                .collection("items").document()

            documentReference.set(item.copy(id = documentReference.id)).await()
            "Berhasil + ${documentReference.id}"
        } catch (e: Exception) {
            "Gagal $e"
        }
    }

    override suspend fun saveUploadImg(
        bitmap: Bitmap,
        name: String,
        quantity: Int,
        rack: String
    ): String {
        return try {
            val imageName = "$name-$rack"
            val storageRef: StorageReference = FirebaseStorage.getInstance().reference
            val imagesRef: StorageReference = storageRef.child("images/$imageName.jpg")
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val data = stream.toByteArray()
            val uploadTask = imagesRef.putBytes(data)
            uploadTask.await()
            val uri = imagesRef.downloadUrl.await()
            uri.toString()
        } catch (e: Exception) {
            "Gagal $e"
        }
    }

    override suspend fun updateItem(item: Item, bitmap: Bitmap?) {
        try {
            if (bitmap != null) {
                val imageName = "${item.name}-${item.rack}"
                val storageRef: StorageReference = FirebaseStorage.getInstance().reference
                val imagesRef: StorageReference = storageRef.child("images/$imageName.jpg")

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val data = stream.toByteArray()

                val uploadTask = imagesRef.putBytes(data)
                uploadTask.await()

                val uri = imagesRef.downloadUrl.await()
                val itemWithImage = item.copy(imageUrl = uri.toString())
                updateItemWithImage(itemWithImage)
            } else {
                firestore.collection("users").document(uid).collection("items")
                    .document(item.id).set(item).await()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateItemWithImage(item: Item) {
        try {
            firestore.collection("users").document(uid)
                .collection("items").document(item.id).set(item).await()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteItem(itemId: String) {
        try {
            firestore.collection("users").document(uid)
                .collection("items").document(itemId).delete().await()
        } catch (e: Exception) {
            "Gagal $e"
        }
    }

    override fun getItemById(itemId: String): Flow<Item> = flow {
        try {
            val snapshot = firestore.collection("users").document(uid)
                .collection("items").document(itemId).get().await()
            val item = snapshot.toObject(Item::class.java)
            if (item != null) {
                emit(item)
            }
        } catch (e: Exception) {
            "Gagal $e"
        }
    }.flowOn(Dispatchers.IO)

    override fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}