package com.kodex.sunny.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudyAppHeader (
    title: String = "",
    subTitle: String = "",
    description: String = "",
){
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