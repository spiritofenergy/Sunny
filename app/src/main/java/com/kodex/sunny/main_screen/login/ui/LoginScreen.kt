package com.kodex.sunny.main_screen.login.ui

import android.R
import android.accessibilityservice.GestureDescription
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.ui.theme.ButtonColor

@Composable
fun LoginScreen() {

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
        Spacer(Modifier.height(30.dp))
        PrimaryButton(
            text = "Продолжить",
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
fun CheckEmailField(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,

    ) {

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            //         textState = it
            //           errorState = if (EMAIL_ADDRESS.matcher(it).matches()) "" else "Некорректный email"

        },
        shape = RoundedCornerShape(10.dp),

        textStyle = MaterialTheme.typography.labelMedium,
        placeholder = {
            Text(
                text = "email@domain.com",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        },
        singleLine = true,
        label = {
            Text(
                text = if (isEmailValid) "email@domain.com" else "Некорректный ввод",
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = Modifier
            .height(40.dp)
            .padding(30.dp, 0.dp)
            .fillMaxWidth(),

        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClicked()
                    //               textState = ""
                    //              errorState = ""
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Иконка очистки поля"

                )
            }
        },
        isError = !isEmailValid && email.isNotEmpty(),

        )
}


@Composable
fun PrimaryButton(
    text: String,
    onRegisterClick: (String) -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
    ) {
    Button(
        shape = RoundedCornerShape(10.dp),
        colors = colors,
        onClick = {
            onRegisterClick(text)
        },

        modifier = Modifier
            .height(45.dp)
            .padding(30.dp, 0.dp)
            .fillMaxWidth(),


    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 16.sp
        )
    }
}

@Composable
fun StudyAppHeader(
    title: String = "",
    subTitle: String = "",
    description: String = "",
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title ,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(50.dp)

        )
        Text(
            text = subTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold

        )
        Text(
            text = description,
            fontSize = 18.sp

        )
    }

}

@Composable
fun MainNavButtons() {
    Row(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
        ) {
            Text(text = "Уроки")
        }
        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
        ) {
            Text(text = "Тесты")
        }
        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
        ) {
            Text(text = "Практика")
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun StudyAppHeaderPreview() {
    StudyAppHeader()
}

@Composable
@Preview(showBackground = true)
fun ShowMainNavButtons() {
    MainNavButtons()

}

