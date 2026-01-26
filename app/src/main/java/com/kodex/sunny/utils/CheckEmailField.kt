package com.kodex.sunny.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
