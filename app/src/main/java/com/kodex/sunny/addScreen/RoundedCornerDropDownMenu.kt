package com.kodex.sunny.addScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.ButtonColor

@Composable
fun RoundedCornerDropDownMenu(
    defCategory: String,
    onOptionSelected: (String) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    val categoryList = stringArrayResource(id = R.array.category_arrays_dr)
    val selectedOption = remember { mutableStateOf(defCategory) }
  //  selectedOption.value = categoryList[defCategory]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = ButtonColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable {
                expanded.value = true
            }
            .padding(20.dp)

            .background(Color.White)
            .clickable {
                expanded.value = true
            }

    ) {
        Text(text = selectedOption.value)

        DropdownMenu(expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }) {
            categoryList.forEach{ option ->
                DropdownMenuItem(
                    text = {
                        Text(text = option)
                    }, onClick = {
                        selectedOption.value = option
                        expanded.value = false
                        onOptionSelected(option)
                    })
            }
        }
    }
}
@Composable
@Preview
fun RoundedCornerDropDownMenuPreview() {
    RoundedCornerDropDownMenu(
        defCategory = "Booking",
        onOptionSelected = {}
    )
}