package com.example.warehouseorganizer.ui.edit

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseorganizer.data.ItemRepository
import com.example.warehouseorganizer.ui.add.AddEvent
import com.example.warehouseorganizer.ui.add.AddUIState
import com.example.warehouseorganizer.ui.add.toItem
import com.example.warehouseorganizer.ui.add.toUIStateItem
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ItemRepository
) : ViewModel() {

    var editUIState by mutableStateOf(AddUIState())
        private set

    private val itemId: String = checkNotNull(savedStateHandle[EditDestination.itemId])

    init {
        viewModelScope.launch {
            repository.getItemById(itemId)
                .filterNotNull()
                .first()
                .toUIStateItem()
                .let { uiStateItem ->
                    editUIState = uiStateItem
                }
        }
    }

    fun updateEditUIState(addEvent: AddEvent) {
        editUIState = editUIState.copy(addEvent = addEvent)
    }

    suspend fun updateItem(bitmap: Bitmap?, name: String, rack: String, quantity: Int) {
        val updatedItem = editUIState.addEvent.toItem().copy(name = name, rack = rack, quantity = quantity)
        repository.updateItem(updatedItem, bitmap)
    }
}
