package com.example.warehouseorganizer.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.warehouseorganizer.WarehouseOrganizerApplication
import com.example.warehouseorganizer.ui.add.AddViewModel
import com.example.warehouseorganizer.ui.detail.DetailViewModel
import com.example.warehouseorganizer.ui.edit.EditViewModel
import com.example.warehouseorganizer.ui.home.HomeViewModel

fun CreationExtras.aplikasiGudang(): WarehouseOrganizerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WarehouseOrganizerApplication)

object ViewModelProviderFactory {
    val Factory = viewModelFactory {

        initializer {
            AddViewModel(aplikasiGudang().appContainer.itemRepository)
        }

        initializer {
            HomeViewModel(aplikasiGudang().appContainer.itemRepository)
        }

        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiGudang().appContainer.itemRepository
            )
        }

        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiGudang().appContainer.itemRepository
            )
        }
    }
}
