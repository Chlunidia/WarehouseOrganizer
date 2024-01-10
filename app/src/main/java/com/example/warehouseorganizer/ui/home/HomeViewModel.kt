package com.example.warehouseorganizer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseorganizer.data.ItemRepository
import com.example.warehouseorganizer.model.Item
import com.example.warehouseorganizer.ui.add.HomeUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class ItemUIState {
    data class Success(val items: Flow<List<Item>>) : ItemUIState()
    object Error : ItemUIState()
    object Loading : ItemUIState()
}

class HomeViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUIState: StateFlow<HomeUIState> = itemRepository.getAllItems()
        .filterNotNull()
        .map {
            HomeUIState(listItems = it.toList(), dataLength = it.size)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUIState()
        )
}
