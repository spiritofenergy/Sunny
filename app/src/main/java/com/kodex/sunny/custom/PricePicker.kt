package com.kodex.spark.ui.custom

import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PricePicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
) {
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                minValue = range.first
                maxValue = range.last
                setOnValueChangedListener { _, _, value ->
                    onValueChange(value)
                }
                this.value = value
            }
        },
        update = {numberPicker->
            numberPicker.minValue = range.first
            numberPicker.maxValue = range.last
                if (numberPicker.value != value){
                    numberPicker.value = value
            }
        }
    )
}