package com.kodex.spark.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PricePicker2(
    title: String = "Min:",
    priceValue: Float = 0F,
    onValueChange: (Float) -> Unit = {}
) {

    Column { Text(text = "$title ${priceValue.toInt()}")
        Slider(
            value = priceValue,
            onValueChange = { value ->
                onValueChange(value)
              },
            valueRange = 0F..5000f,
            steps = 99
        )
    }
}