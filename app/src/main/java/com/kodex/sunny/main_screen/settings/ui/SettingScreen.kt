package com.kodex.gpstracker.main_screen.settings.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kodex.sunny.R
import com.kodex.sunny.custom.CustomDropDownMenu
import com.kodex.sunny.main_screen.settings.data.ColorPickerData
import com.kodex.sunny.main_screen.settings.ui.ColorPickerItem


@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val optionUpdateTime = listOf("3000", "5000", "10000")
    val optionPriority = listOf(
        "PRIORITY_HIGH_ACCURACY",
        "PRIORITY_HIGH_POWER",
        "PRIORITY_PASSIVE",
        "PRIORITY_BALANCED_POWER_ACCURACY"
    )
    val optionsTrackLineWidth = listOf("5", "10", "15")
    val optionsTrackLineColor = listOf(
        ColorPickerData(color = Color.Blue),
        ColorPickerData(color = Color.Red),
        ColorPickerData(color = Color.Green),
        ColorPickerData(color = Color.Magenta),
    )

    val selectedUpdateTime = remember {
        mutableStateOf(viewModel.getLocationUpdateInterval())
    }
    val selectedPriority = remember {
        mutableStateOf(viewModel.getPriority())
        // Priority.PRIORITY_HIGH_ACCURACY
    }
    val selectedTrackLineWidth = remember {
        mutableStateOf(viewModel.getTrackLineWidth())
    }

    val colorListState = remember {
        mutableStateOf(
            optionsTrackLineColor.map { colorPickerData ->
                colorPickerData.copy(
                    isChecked = colorPickerData.color.value.toString() == viewModel.getTraCKColor())
            })
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    )

    {/*1*/
        Box(modifier = Modifier.fillMaxWidth()
            .height(200.dp)){

        }

        Text(
            text = stringResource(R.string.location_update_time),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        // Local update time
        CustomDropDownMenu(
            option = optionUpdateTime,
            selectedOption = selectedUpdateTime.value,
        ) { selectedOption ->
            selectedUpdateTime.value = selectedOption
            viewModel.saveLocationUpdateInterval(selectedOption)
        }
        Spacer(modifier = Modifier.height(10.dp))

        /*2*/
        Text(
            text = stringResource(R.string.priority),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        //Priority
        CustomDropDownMenu(
            option = optionPriority,
            selectedOption = selectedPriority.value,
        ) { selectedOption ->
            selectedPriority.value = selectedOption
            viewModel.savePriority(selectedOption)
        }

        /*3*/
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.track_line_width),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
        )
        CustomDropDownMenu(
            option = optionsTrackLineWidth,
            selectedOption = selectedTrackLineWidth.value,
        ) { selectedOption ->
            selectedTrackLineWidth.value = selectedOption
            viewModel.saveTrackLineWidth(selectedOption)
        }

        /*4*/
        Text(
            text = stringResource(R.string.track_line_color),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,

            )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(colorListState.value) { colorPickerData ->
                ColorPickerItem(colorPickerData) {
                    viewModel.saveColor(colorPickerData.color)
                    colorListState.value = colorListState.value.map { cpd ->
                        cpd.copy(
                            isChecked = cpd.color == colorPickerData.color
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

        }
    }
}
