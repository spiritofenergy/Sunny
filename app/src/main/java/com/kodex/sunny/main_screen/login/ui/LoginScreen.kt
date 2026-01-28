package com.kodex.sunny.main_screen.login.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.ButtonColor
import com.kodex.sunny.ui.theme.ButtonColorDark


@Composable
fun LoginScreen() {
    val auth = remember {
        Firebase.auth
    }
    var errorState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }

   /* val currentUser = auth.currentUser
    if (currentUser != null) {
        signUp(
            auth, currentUser.email ?: "", passwordState,
            onSignInSuccess = {
                Log.d("TAG", "signIn: success3")
            },
            onSignInFailure = { error ->
                errorState = error
                Log.d("TAG", "signIn: failure3: $error")
            }
        )
    }*/

    Box(
        modifier = Modifier.fillMaxSize()
            .background(ButtonColorDark)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.way
            ),
            contentDescription = "BG",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(46.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.icon_two),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(250.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Sunny",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(40.dp))
            RoundedCornerTextField(
                text = emailState,
                label = "Логин:",
                isPassword = false
            ) { emailState = it }
            Spacer(modifier = Modifier.height(10.dp))
            RoundedCornerTextField(
                text = passwordState,
                label = "Пароль:",
                isPassword = true
            ) {
                passwordState = it
            }

            Spacer(modifier = Modifier.height(10.dp))
            if (errorState.isNotEmpty()) Text(
                text = errorState,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(
                text = "Войти",
                onRegisterClick = {
                    Log.d("TAG", "LoginScreen: $it")
                },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            )

            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(
                text = "Регистрация",
                onRegisterClick = {
                    signUp(
                        auth, emailState, passwordState,
                        {
                            Log.d("TAG", "signIn: success1")
                        },
                        { error ->
                            errorState = error
                            Log.d("TAG", "signIn: failure1: $error")
                        }
                    )
                },
                )
            Spacer(modifier = Modifier.height(10.dp))

            LoginButton(
                text = "Регистрация",
                onClick = {
                    signUp(
                        auth, emailState, passwordState,
                        onSignInSuccess = {
                            Log.d("TAG", "signIn: success2")
                        },
                        onSignInFailure = { error ->
                            errorState = error
                            Log.d("TAG", "signIn: failure2: $error")
                        }
                    )
                }
            )
        }
    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: () -> Unit,
    onSignInFailure: (String) -> Unit
) {

    if (email.isEmpty() || password.isEmpty()) {
        onSignInFailure("Email or password is empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSuccess()
        }
        .addOnFailureListener { it ->
            onSignInFailure(it.message ?: "SignIn Error")
            Log.d("TAG", "signIn: ${it.message}")
        }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
     )
}



