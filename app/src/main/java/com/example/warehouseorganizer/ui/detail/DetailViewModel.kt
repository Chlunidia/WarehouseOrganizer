package com.example.warehouseorganizer.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseorganizer.data.ItemRepository
import com.example.warehouseorganizer.ui.add.DetailUIState
import com.example.warehouseorganizer.ui.add.toDetailItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ItemRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val itemId: String = checkNotNull(savedStateHandle[DetailDestination.itemId])

    val uiState: StateFlow<DetailUIState> =
        repository.getItemById(itemId)
            .filterNotNull()
            .map {
                DetailUIState(addEvent = it.toDetailItem())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailUIState()
            )

    suspend fun deleteItem() {
        repository.deleteItem(itemId)
    }
}