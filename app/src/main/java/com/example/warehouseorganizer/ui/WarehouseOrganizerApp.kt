package com.example.warehouseorganizer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.warehouseorganizer.navigation.MainPageManager
import com.example.warehouseorganizer.navigation.PageManager
import com.example.warehouseorganizer.ui.add.WarehouseOrganizerTopAppBar
import com.example.warehouseorganizer.ui.home.BottomNavigationBar
import com.example.warehouseorganizer.ui.home.DestinasiHome
import com.example.warehouseorganizer.ui.home.NavItem
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun WarehouseOrganizerApp() {
    val navController = rememberNavController()
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    PageManager(
        navController = navController,
        isLoggedIn = isLoggedIn
    )
}