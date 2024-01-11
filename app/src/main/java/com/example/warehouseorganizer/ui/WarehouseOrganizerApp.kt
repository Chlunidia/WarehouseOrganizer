package com.example.warehouseorganizer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.warehouseorganizer.navigation.PageManager
import com.example.warehouseorganizer.ui.home.BottomNavigationBar
import com.example.warehouseorganizer.ui.home.NavItem
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseOrganizerApp() {
    val navController = rememberNavController()
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    var selectedTab by remember { mutableStateOf(NavItem.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    when (tab) {
                        NavItem.Home -> navController.navigate("homeScreen")
                        NavItem.Profile -> navController.navigate("profileScreen")
                        // Add other cases for each tab
                        else -> {}
                    }
                },
                navigateToItemEntry = { navController.navigate("addScreen") },
                navigateToProfile = { navController.navigate("profileScreen") },
                navigateToHome = { navController.navigate("homeScreen") }
            )
        }
    ) {
        PageManager(navController = navController, isLoggedIn = isLoggedIn, selectedTab = mutableStateOf(selectedTab))
    }
}
