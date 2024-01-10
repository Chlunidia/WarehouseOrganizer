package com.example.warehouseorganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import com.example.warehouseorganizer.ui.add.AddScreen
import com.example.warehouseorganizer.ui.home.HomeScreen
import com.example.warehouseorganizer.ui.login.LoginScreen
import com.example.warehouseorganizer.ui.login.LoginViewModel
import com.example.warehouseorganizer.ui.signup.SignUpScreen
import com.example.warehouseorganizer.ui.signup.SignUpViewModel

@Composable
fun PageManager(navController: NavController, isLoggedIn: Boolean) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = if (isLoggedIn) "HomeScreen" else "LoginScreen"
    ) {
        composable("SignUpScreen") {
            SignUpScreen(
                navController = navController,
                signUpViewModel = SignUpViewModel(),
                onSignUpSuccess = {
                    navController.navigate("LoginScreen")
                }
            )
        }
        composable("LoginScreen") {
            LoginScreen(
                navController = navController,
                loginViewModel = LoginViewModel(),
                onLoginSuccess = {
                    navController.navigate("HomeScreen")
                }
            )
        }
        composable("AddScreen") {
            AddScreen(
                navigateBack = { navController.popBackStack() },
                modifier = Modifier,
                addViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
            )
        }
        composable("HomeScreen") {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate("AddScreen")
                },
                onDetailClick = { itemId -> },
                viewModel = viewModel(factory = ViewModelProviderFactory.Factory)
            )
        }
    }
}