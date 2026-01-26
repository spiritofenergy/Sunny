package com.kodex.sunny.main_screen.login.ui

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kodex.sunny.main_screen.login.data.LoginViewModel
import com.kodex.sunny.utils.CheckEmailField
import com.kodex.sunny.utils.PrimaryButton
import com.kodex.sunny.utils.StudyAppHeader

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
//onNavigationToMainScreen: (HomeScreenDataObject) ->Unit
) {

    var userEmail by remember { mutableStateOf("") }
    var isEmailFormatValid by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }
    val testEmail = "email@domain.com"

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        StudyAppHeader(
            title = "Sunny",
            subTitle = "Войти в аккаунт",
            description = "Введите почту чтобы войти"
        )

        Spacer(Modifier.height(40.dp))
        CheckEmailField(
            email = userEmail,
            isEmailValid = isEmailFormatValid,
            onEmailChange = {
                userEmail = it
                isEmailFormatValid = EMAIL_ADDRESS.matcher(it).matches()
                validationMessage = if (!isEmailFormatValid) {
                    "Некорректный email"
                } else ""
            },
            onClearClicked = {
                userEmail= ""
                isEmailFormatValid = true
                validationMessage = ""
            }
        )
        Spacer(Modifier.height(20.dp))
        PrimaryButton(
            text = "Вход",
            onRegisterClick = {it: String ->
                validationMessage =
                    if(userEmail.isEmpty()|| !isEmailFormatValid) {
                        "Некорректный email"
                    }else if (userEmail == testEmail){
                        "Такая почта уже существует"
                    }else {
                        "Регистрация успешна"
                    }
                Log.i("!!!", "PrimaryButton: $it")
            }
        )
        Spacer(Modifier.height(20.dp))
        PrimaryButton(
            text = "Регистрация",
            onRegisterClick = {it: String ->
                validationMessage =
                    if(userEmail.isEmpty()|| !isEmailFormatValid) {
                        "Некорректный email"
                    }else if (userEmail == testEmail){
                        "Такая почта уже существует"
                    }else {
                        "Регистрация успешна"
                    }
                Log.i("!!!", "PrimaryButton: $it")
            }
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = validationMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = if (validationMessage.contains("успешна")) Color.DarkGray else Color.Red,
            modifier = Modifier.alpha(if (validationMessage.isNotEmpty() && isEmailFormatValid) 1f else 0f)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun StudyAppHeaderPreview() {
    LoginScreen(

    )
}



