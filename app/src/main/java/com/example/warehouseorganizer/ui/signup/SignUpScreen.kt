package com.example.warehouseorganizer.ui.signup

import android.content.Context
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import com.example.warehouseorganizer.R
import com.example.warehouseorganizer.navigation.NavigationDestination
import com.example.warehouseorganizer.ui.theme.textFieldModifier

object DestinasiSignUp : NavigationDestination {
    override val route = "signup"
    override val titleRes = "SignUp"
}

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    onSignUpSuccess: () -> Unit
)  {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background_page),
                contentScale = ContentScale.FillHeight
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(450.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                )
                .padding(16.dp)
        ) {
            SignUpForm(signUpViewModel, onSignUpSuccess, context)
        }
    }
}

@Composable
fun SignUpForm(
    signUpViewModel: SignUpViewModel,
    onSignUpSuccess: () -> Unit,
    context: Context
) {
    val usernameState by signUpViewModel.usernameState.collectAsState()
    val emailState by signUpViewModel.emailState.collectAsState()
    val passwordState by signUpViewModel.passwordState.collectAsState()
    val signUpState by signUpViewModel.signUpState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.Black
        )

        TextField(
            value = usernameState,
            onValueChange = { signUpViewModel.onUsernameChange(it) },
            label = { Text(text = "Type your Username") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(parseColor("#e0f5f4")),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(parseColor("#5E5E5E")),
                unfocusedLabelColor = Color(parseColor("#5E5E5E"))),
            modifier = textFieldModifier
        )

        TextField(
            value = emailState,
            onValueChange = { signUpViewModel.onEmailChange(it) },
            label = { Text(text = "Type your Email") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(parseColor("#e0f5f4")),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(parseColor("#5E5E5E")),
                unfocusedLabelColor = Color(parseColor("#5E5E5E"))),
            modifier = textFieldModifier
        )

        OutlinedTextField(
            value = passwordState,
            onValueChange = { signUpViewModel.onPasswordChange(it) },
            label = { Text(text = "Type your Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(parseColor("#e0f5f4")),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(parseColor("#5E5E5E")),
                unfocusedLabelColor = Color(parseColor("#5E5E5E"))),
            modifier = textFieldModifier
        )

        Button(
            onClick = {
                signUpViewModel.signUpUser(onSignUpSuccess)
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
                text = "Sign Up",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}