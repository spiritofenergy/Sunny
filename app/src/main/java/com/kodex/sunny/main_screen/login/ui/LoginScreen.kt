package com.kodex.sunny.main_screen.login.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kodex.bookmarket.navigation.NavRoutes
import com.kodex.sunny.R
import com.kodex.sunny.custom_ui.Progress
import com.kodex.sunny.ui.theme.ButtonColorDark
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    onNavigateToMainScreen: (NavRoutes.MainScreenDataObject) -> Unit = {}
) {
    val auth = remember {
        Firebase.auth
    }
    var errorState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("nigmatullov@mail.ru") }
    var passwordState by remember { mutableStateOf("test2401") }
    var successState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ButtonColorDark)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.bich_1
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
                    .padding(bottom = 50.dp)
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
                    signIn(
                        auth, emailState, passwordState,
                        onSignInSuccess = { navData ->
                            onNavigateToMainScreen(navData)
                            successState = true
                            errorState = "We invite you to the sunny city"
                            Log.d("TAG", "LoginScreen: $it")
                        },
                        onSignInFailure = { error ->
                            errorState = error
                            Log.d("TAG", "signIn: failure: $error")
                        },
                    )
                },
            )

            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(
                text = "Регистрация",
                onRegisterClick = {
                    signUp(
                        auth, emailState, passwordState,
                        { navData ->
                            onNavigateToMainScreen(navData)
                            successState = true
                            errorState = "We invite you to the sunny city"
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
            if (successState == true){
                Progress()
                LaunchedEffect(Unit) {
                    delay(2000)
                    successState = false
                    errorState = ""

                }
            }

        }
    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (NavRoutes.MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {

    if (email.isEmpty() || password.isEmpty()) {
        onSignInFailure("Email or password is empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSuccess(
                NavRoutes.MainScreenDataObject(
                    uid = auth.currentUser?.uid ?: "",
                    email = auth.currentUser?.email ?: ""
                )
            )
        }
        .addOnFailureListener { it ->
            onSignInFailure(it.message ?: "SignIn Error")
            Log.d("TAG", "signIn: ${it.message}")
        }
}


fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (NavRoutes.MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {

    if (email.isEmpty() || password.isEmpty()) {
        onSignInFailure("Email or password is empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSuccess(
                NavRoutes.MainScreenDataObject(
                    uid = auth.currentUser?.uid ?: "",
                    email = auth.currentUser?.email ?: ""
                )
            )
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



