package com.example.warehouseorganizer.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.warehouseorganizer.navigation.NavigationDestination

object DestinasiProvile : NavigationDestination {
    override val route = "profile"
    override val titleRes = "Profile"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val username by profileViewModel.usernameState.collectAsState()
    val email by profileViewModel.emailState.collectAsState()
    val photoUrl by profileViewModel.photoUrlState.collectAsState()
    var isLogoutDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberImagePainter(data = photoUrl),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .padding(top = 50.dp)
                .size(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(20.dp),
            value = username ?: "",
            onValueChange = {},
            label = { Text("Username") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            shape = RoundedCornerShape(20.dp),
            value = email ?: "",
            onValueChange = {},
            label = { Text("Email") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { isLogoutDialogVisible = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text("Logout")
        }
        if (isLogoutDialogVisible) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                onDismissRequest = { isLogoutDialogVisible = false },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    Button(
                        onClick = {
                            isLogoutDialogVisible = false
                            profileViewModel.logout()
                            navController.navigate("loginScreen")
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isLogoutDialogVisible = false },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}
