package com.example.warehouseorganizer.ui.add

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.warehouseorganizer.data.ItemRepository

class AddViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    var addUIState by mutableStateOf(AddUIState())
        private set

    fun updateAddUIState(addEvent: AddEvent) {
        addUIState = AddUIState(addEvent = addEvent)
    }

    suspend fun saveItem(bitmap: Bitmap, name: String, rack: String, quantity: Int) {
        val result = itemRepository.saveItemWithImage(bitmap, name, rack, quantity)
        println(result)
    }

}