package com.example.warehouseorganizer.ui.login

import android.graphics.Color.parseColor
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.warehouseorganizer.R
import com.example.warehouseorganizer.navigation.NavigationDestination
import kotlinx.coroutines.launch

object DestinasiLogin : NavigationDestination {
    override val route = "login"
    override val titleRes = "Login"
}

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val emailState = loginViewModel.emailState
    val passwordState = loginViewModel.passwordState
    val loginState by loginViewModel.loginState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .paint(
                painter = painterResource(id = R.drawable.background_page),
                contentScale = ContentScale.FillHeight
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val (topText, column) = createRefs()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .constrainAs(column) {
                        bottom.linkTo(parent.bottom)
                    }
                    .background(
                        color = Color(parseColor("#ffffff")),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    )
            ) {
                Text(
                    text = "Login",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { loginViewModel.onEmailChange(it) },
                    label = { Text(text = "Type your Email") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .background(Color.White, CircleShape)
                )
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text(text = "Type your Password") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .background(Color.White, CircleShape)
                )
                Button(
                    onClick = {
                        loginViewModel.viewModelScope.launch {
                            loginViewModel.loginUser()
                            val loginResult = loginViewModel.loginState.value
                            when (loginResult) {
                                is LoginResult.Success -> {
                                    onLoginSuccess()
                                }

                                is LoginResult.Error -> {
                                }

                                else -> {}
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    Text(
                        text = "Sign Up",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("signUpScreen")
                            }
                    )
                }
            }
        }
    }
}
