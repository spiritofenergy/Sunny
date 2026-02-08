package com.kodex.sunny.custom_ui

import android.R.attr.description
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyDialog(
    showDialog: Boolean,
    onDismiss: ()-> Unit,
    onConfirm: ()-> Unit,
    title: String = "Reset Password",
    massage: String,
    confirmButtonText: String = "Да"
) {
    if (showDialog){
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            confirmButton ={
                Button(onClick = {
                    onConfirm()
                }) {
                    Text(text = confirmButtonText)
                }
                Button(onClick = {
                    onDismiss()
                }) {
                    Text(text = "Cansel")
                }
            },
            title= {
                Text(
                    text = title,
                    color = Color.Red,
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    text = massage,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        )
    }
}