package com.example.warehouseorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.warehouseorganizer.ui.WarehouseOrganizerApp
import com.example.warehouseorganizer.ui.login.LoginViewModel
import com.example.warehouseorganizer.ui.signup.DestinasiSignUp
import com.example.warehouseorganizer.ui.signup.SignUpViewModel
import com.example.warehouseorganizer.ui.theme.WarehouseOrganizerTheme

class WarehouseOrganizerApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarehouseOrganizerTheme {
                WarehouseOrganizerApp()
            }
        }
    }
}