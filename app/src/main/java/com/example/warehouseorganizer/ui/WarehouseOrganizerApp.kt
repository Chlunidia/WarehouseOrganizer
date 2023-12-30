package com.example.warehouseorganizer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.warehouseorganizer.navigation.PageManager
import com.example.warehouseorganizer.ui.login.LoginViewModel

@Composable
fun WarehouseOrganizerApp(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    PageManager(
        navController = navController,
        loginViewModel = loginViewModel
    )
}
