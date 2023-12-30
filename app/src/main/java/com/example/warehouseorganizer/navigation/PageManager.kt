package com.example.warehouseorganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.warehouseorganizer.ui.home.DestinasiHome
import com.example.warehouseorganizer.ui.home.HomeScreen
import com.example.warehouseorganizer.ui.login.DestinasiLogin
import com.example.warehouseorganizer.ui.login.LoginScreen
import com.example.warehouseorganizer.ui.login.LoginViewModel

@Composable
fun PageManager(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = Modifier,
    ) {
        composable(DestinasiLogin.route) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            ) {
                navController.navigate(DestinasiHome.route)
            }
        }
        composable(DestinasiHome.route) {
            HomeScreen(navController = navController)
        }
    }
}
