package com.example.warehouseorganizer.ui.edit

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseorganizer.navigation.NavigationDestination
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import com.example.warehouseorganizer.ui.add.EntryBody
import com.example.warehouseorganizer.ui.add.WarehouseOrganizerTopAppBar
import kotlinx.coroutines.launch

object EditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = "Edit Item"
    const val itemId = "itemId"
    val routeWithArgs = "$route/{$itemId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            WarehouseOrganizerTopAppBar(
                title = EditDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val onSaveClick: (Bitmap?, String, String, Int) -> Unit = { bitmap, name, rack, quantity ->
            coroutineScope.launch {
                viewModel.updateItem()
                navigateBack()
            }
        }

        EntryBody(
            addUIState = viewModel.addUIState,
            onItemValueChange = viewModel::updateAddUIState,
            onSaveClick = onSaveClick,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}
