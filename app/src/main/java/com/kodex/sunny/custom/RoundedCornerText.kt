package com.kodex.sunny.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.ui.theme.WhiteTransparent

@Composable
fun RoundedCornerText (
    text: String,
    color: Color = Color.Black,
    modifier: Modifier = Modifier,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal

){
    Text(text = text,
        color = color,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        modifier = modifier
            .background(
                WhiteTransparent,
                shape = RoundedCornerShape(10.dp)
            ).padding(horizontal = 10.dp, vertical = 5.dp))
}