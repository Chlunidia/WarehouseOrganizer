package com.example.warehouseorganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import com.example.warehouseorganizer.ui.add.AddScreen
import com.example.warehouseorganizer.ui.detail.DetailDestination
import com.example.warehouseorganizer.ui.detail.DetailScreen
import com.example.warehouseorganizer.ui.edit.EditDestination
import com.example.warehouseorganizer.ui.edit.EditScreen
import com.example.warehouseorganizer.ui.home.HomeScreen
import com.example.warehouseorganizer.ui.login.LoginScreen
import com.example.warehouseorganizer.ui.login.LoginViewModel
import com.example.warehouseorganizer.ui.profile.ProfileScreen
import com.example.warehouseorganizer.ui.signup.SignUpScreen
import com.example.warehouseorganizer.ui.signup.SignUpViewModel

@Composable
fun PageManager(navController: NavController, isLoggedIn: Boolean) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = if (isLoggedIn) "homeScreen" else "loginScreen"
    ) {
        composable("signUpScreen") {
            SignUpScreen(
                navController = navController,
                signUpViewModel = SignUpViewModel(),
                onSignUpSuccess = {
                    navController.navigate("loginScreen")
                }
            )
        }
        composable("loginScreen") {
            LoginScreen(
                navController = navController,
                loginViewModel = LoginViewModel(),
                onLoginSuccess = {
                    navController.navigate("homeScreen")
                }
            )
        }
        composable(route = "addScreen") {
            AddScreen(
                navigateBack = { navController.popBackStack() },
                context = LocalContext.current,  // Provide the context here
                modifier = Modifier,
                addViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
            )
        }
        composable("homeScreen") {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate("addScreen")
                },
                navigateToHome = {
                    navController.navigate("homeScreen")
                },
                navigateToProfile = {
                    navController.navigate("profileScreen")
                },
                onDetailClick = { item ->
                    navController.navigate("${DetailDestination.route}/${item.id}")
                },)
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.itemId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(DetailDestination.itemId)
            itemId?.let {
                DetailScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = {
                        navController.navigate("${EditDestination.route}/$itemId")
                    }
                )
            }
        }
        composable(
            route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.itemId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(EditDestination.itemId)
            itemId?.let {
                EditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
        composable("profileScreen") {
            ProfileScreen()
        }
    }
}
