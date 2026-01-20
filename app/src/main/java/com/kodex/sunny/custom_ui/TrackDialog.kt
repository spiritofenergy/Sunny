package com.kodex.sunny.custom_ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kodex.sunny.R
import com.kodex.sunny.utils.TimeUtils

@Composable
fun TrackDialog(
    title: String,
    showDialog: Boolean,
    dialogType: DialogType = DialogType.SAVE,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var trackName by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                trackName = ""
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                        trackName = ""
                    }
                ) {
                    Text(text = "Cansel")
                }
                Button(
                    onClick = {
                        onConfirm(
                            trackName.ifEmpty {
                                "Track_${TimeUtils.getDateAndTime()}"
                            })
                        trackName = ""
                    }
                ) {
                    Text(text = "Ok")
                }
            },
            title = {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            },
            text = {
                if (dialogType == DialogType.SAVE){
                    TextField(
                        value = trackName,
                        onValueChange = { text ->
                            trackName = text
                        },
                        label = {
                            Text(text = "EnterTrack name")
                        }
                    )
            }else {
            Text(text = stringResource(R.string.delete_dialog_massage))
                    }
               }
         )
    }
}
enum class DialogType {
    DELETE, SAVE
}