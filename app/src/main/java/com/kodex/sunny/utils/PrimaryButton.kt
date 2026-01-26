package com.kodex.sunny.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.ui.theme.ButtonColor

@Composable
fun PrimaryButton(
    text: String,
    onRegisterClick: (String) -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
)
{
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