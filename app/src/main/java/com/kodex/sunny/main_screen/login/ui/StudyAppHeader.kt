package com.kodex.sunny.main_screen.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.R


@Composable
fun StudyAppHeader() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.i),
            contentDescription = "ИСКРА"
        )
        Text(
            text = "Sunny",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(50.dp)

        )
        Text(
            text = "Войти в аккаунт",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold

        )
        Text(
            text = "Введите почту чтобы войти",
            fontSize = 18.sp

        )
    }
}
@Composable
@Preview
fun StudyAppHeaderPreview() {
    StudyAppHeader()
}