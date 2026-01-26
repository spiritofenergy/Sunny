package com.kodex.gpstracker.main_screen.tracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.kodex.sunny.R
import com.kodex.sunny.main_screen.tracker.data.TrackData


@Composable
fun TrackItemUi(
    trackData: TrackData,
    onDeleteClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth()
           // .background(color = Color.LightGray)
            .padding(start = 2.dp, end = 2.dp, top = 0.5.dp, bottom = 0.5.dp)
            .clickable {
                onItemClick()
            }

    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp)
            )
        {
            Text(
                text = stringResource(R.string.date) + ": ${trackData.date}",
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = trackData.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(text = stringResource(R.string.time) + ": Time: ${trackData.time}",
                color = Color.Red)

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.distance) + ": ${trackData.distance} km",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        onDeleteClick()
                    }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = ""
                    )
                }

            }
        }
    }
}