package com.example.warehouseorganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.warehouseorganizer.data.FirebaseAuthRepository
import com.example.warehouseorganizer.ui.ViewModelProviderFactory
import com.example.warehouseorganizer.ui.add.AddScreen
import com.example.warehouseorganizer.ui.detail.DetailDestination
import com.example.warehouseorganizer.ui.detail.DetailScreen
import com.example.warehouseorganizer.ui.edit.EditDestination
import com.example.warehouseorganizer.ui.edit.EditScreen
import com.example.warehouseorganizer.ui.home.BottomNavigationBar
import com.example.warehouseorganizer.ui.home.HomeScreen
import com.example.warehouseorganizer.ui.home.NavItem
import com.example.warehouseorganizer.ui.login.LoginScreen
import com.example.warehouseorganizer.ui.login.LoginViewModel
import com.example.warehouseorganizer.ui.profile.ProfileScreen
import com.example.warehouseorganizer.ui.signup.SignUpScreen
import com.example.warehouseorganizer.ui.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PageManager(navController: NavController, isLoggedIn: Boolean, selectedTab: MutableState<NavItem>) {
    var selectedTab by remember { mutableStateOf(NavItem.Home) }

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
            val loginRepository = FirebaseAuthRepository(FirebaseAuth.getInstance())
            val loginViewModel = LoginViewModel(loginRepository)

            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("homeScreen")
                }
            )
        }
        composable(route = "addScreen") {
            AddScreen(
                navigateBack = { navController.popBackStack() },
                context = LocalContext.current,
                modifier = Modifier,
                addViewModel = viewModel(factory = ViewModelProviderFactory.Factory)
            )
        }
        composable("homeScreen") {
            HomeScreen(
                onDetailClick = { item ->
                    navController.navigate("${DetailDestination.route}/${item.id}")
                }
            )
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
            ProfileScreen(navController)
        }
    }
    BottomNavigationBar(
        selectedTab = selectedTab,
        onTabSelected = { tab ->
        },
        navigateToItemEntry = { navController.navigate("addScreen") },
        navigateToProfile = { navController.navigate("profileScreen") },
        navigateToHome = { navController.navigate("homeScreen") }
    )
}
