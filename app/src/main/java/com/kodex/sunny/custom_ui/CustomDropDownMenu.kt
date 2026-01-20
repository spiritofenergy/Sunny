package com.kodex.sunny.custom_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDropDownMenu(
    option: List<String>,
    selectedOption: String,
    onOptionSelected: (String)-> Unit

    ){

    val expanded = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent)
            .clickable{
                expanded.value = true
            }
            .padding(15.dp)
    ){
        Text(
            text = selectedOption,
            color = Color.Gray
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }
        ){
            option.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(text = option)
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}