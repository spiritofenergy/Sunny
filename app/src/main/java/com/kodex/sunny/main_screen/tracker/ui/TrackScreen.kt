package com.kodex.gpstracker.main_screen.tracker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kodex.sunny.R
import com.kodex.sunny.custom.DialogType
import com.kodex.sunny.custom.TrackDialog
import com.kodex.sunny.main_screen.tracker.data.TrackData
import com.kodex.sunny.utils.ProgressBar
import kotlinx.coroutines.delay
import kotlin.collections.emptyList


@Composable
fun TrackerScreen(
    viewModel: TrackViewModel = hiltViewModel(),
    onTrackClick: (TrackData) -> Unit = {},
) {
    val trackList = viewModel.trackList.collectAsState(initial = emptyList())
    val showDialog = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        if (trackList.value.isEmpty()){
            ProgressBar()
            LaunchedEffect(Unit) {
                delay(1000)
            }
           // Text(text = stringResource(R.string.no_tracks))

        }else{
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding( 2.dp))
            {
                items(trackList.value) { trackItem ->
                    TrackItemUi(
                        trackItem,
                        onDeleteClick = {
                            showDialog.value = true
                            viewModel.trackToDelete = trackItem

                        },
                        onItemClick = {
                                onTrackClick(trackItem)
                        }
                    )
                    Spacer(Modifier.padding(5.dp))
                }
            }
        }
    }

    TrackDialog(
        title = stringResource(R.string.delete_dialog_massage),
        showDialog = showDialog.value,
        dialogType = DialogType.DELETE,
        onDismiss = { showDialog.value = false },
        onConfirm = {
            viewModel.deleteTrack()
            showDialog.value = false
        }
    )
}

