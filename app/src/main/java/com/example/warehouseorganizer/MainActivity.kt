package com.example.warehouseorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.warehouseorganizer.ui.WarehouseOrganizerApp
import com.example.warehouseorganizer.ui.login.LoginViewModel
import com.example.warehouseorganizer.ui.theme.WarehouseOrganizerTheme

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarehouseOrganizerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WarehouseOrganizerApp(
                        navController = rememberNavController(),
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}