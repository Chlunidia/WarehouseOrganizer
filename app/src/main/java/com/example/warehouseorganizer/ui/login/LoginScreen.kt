package com.example.warehouseorganizer.ui.login

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.warehouseorganizer.R
import com.example.warehouseorganizer.data.LoginRepository
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
                    .height(350.dp)
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
                        .align(Alignment.CenterHorizontally), // Center horizontally
                    color = Color.Black
                )

                TextField(
                    value = emailState.value,
                    onValueChange = { loginViewModel.onEmailChange(it) },
                    label = { Text(text = "Type your Email") },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(parseColor("#e0f5f4")),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color(parseColor("#5E5E5E")),
                        unfocusedLabelColor = Color(parseColor("#5E5E5E"))
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .background(Color.White, CircleShape)
                )

                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    label = { Text(text = "Type your Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(parseColor("#e0f5f4")),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        textColor = Color(parseColor("#5E5E5E")),
                        unfocusedLabelColor = Color(parseColor("#5E5E5E"))
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .background(Color.White, CircleShape)
                )

                Button(
                    onClick = {
                        loginViewModel.viewModelScope.launch {
                            // Panggil metode loginUser
                            loginViewModel.loginUser()

                            // Ambil nilai loginState setelah loginUser selesai
                            val loginResult = loginViewModel.loginState.value

                            // Tambahkan logika untuk menangani hasil login
                            when (loginResult) {
                                is LoginState.Success -> {
                                    // Jika login berhasil, panggil callback onLoginSuccess
                                    onLoginSuccess()
                                }
                                is LoginState.Error -> {
                                    // Tampilkan pesan error jika login gagal
                                    // Anda juga bisa menambahkan logika lain sesuai kebutuhan
                                    // Contoh: menampilkan snackbar, dialog, dsb.
                                }
                                // Tambahkan penanganan state lainnya jika diperlukan
                                // Contoh: Loading state
                                else -> {}
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(parseColor("#faac64"))
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}