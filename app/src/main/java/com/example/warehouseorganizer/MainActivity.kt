package com.example.warehouseorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.warehouseorganizer.ui.WarehouseOrganizerApp
import com.example.warehouseorganizer.ui.theme.WarehouseOrganizerTheme

class WarehouseOrganizerApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarehouseOrganizerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WarehouseOrganizerApp()
                }
            }
        }
    }
}