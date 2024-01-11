package com.example.warehouseorganizer.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.warehouseorganizer.navigation.NavigationDestination

object DestinasiProvile : NavigationDestination {
    override val route = "profile"
    override val titleRes = "Profile"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
) {
    val username by profileViewModel.usernameState.collectAsState()
    val email by profileViewModel.emailState.collectAsState()
    val photoUrl by profileViewModel.photoUrlState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Picture
        Image(
            painter = rememberImagePainter(data = photoUrl),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clip(MaterialTheme.shapes.large)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Username TextField
        TextField(
            value = username ?: "",
            onValueChange = {},
            label = { Text("Username") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email TextField
        TextField(
            value = email ?: "",
            onValueChange = {},
            label = { Text("Email") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
        )
    }
}
