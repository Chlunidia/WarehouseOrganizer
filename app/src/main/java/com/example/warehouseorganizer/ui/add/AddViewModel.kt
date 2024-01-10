package com.example.warehouseorganizer.ui.add

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.warehouseorganizer.data.ItemRepository

class AddViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    var addUIState by mutableStateOf(AddUIState())
        private set

    // New property to hold the selected image URI
    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    fun updateAddUIState(addEvent: AddEvent) {
        addUIState = AddUIState(addEvent = addEvent)
    }

    // Function to set the selected image URI
    fun setSelectedImageUri(uri: Uri?) {
        selectedImageUri = uri
    }

    suspend fun addItem() {
        itemRepository.saveItem(addUIState.addEvent.toItem())
    }
}