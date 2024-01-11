package com.example.warehouseorganizer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.warehouseorganizer.navigation.MainPageManager
import com.example.warehouseorganizer.ui.add.WarehouseOrganizerTopAppBar
import com.example.warehouseorganizer.ui.home.BottomNavigationBar
import com.example.warehouseorganizer.ui.home.DestinasiHome
import com.example.warehouseorganizer.ui.home.NavItem
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    onClickedSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    var selectedTab by remember { mutableStateOf(NavItem.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WarehouseOrganizerTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                },
                navigateToItemEntry = { navController.navigate("addScreen") },
                navigateToProfile = { navController.navigate("profileScreen") },
                navigateToHome = { navController.navigate("homeScreen") }
            )
        }
    ) {
        MainPageManager(
            onClickedSignOut = onClickedSignOut,
            navController = navController,
            isLoggedIn = isLoggedIn,
            selectedTab = mutableStateOf(selectedTab)
        )
    }
}