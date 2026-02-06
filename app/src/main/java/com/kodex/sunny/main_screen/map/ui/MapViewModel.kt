package com.kodex.sunny.main_screen.map.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodex.sunny.custom_ui.db.MainDb
import com.kodex.sunny.location.LocationDataSharer
import com.kodex.sunny.main_screen.tracker.data.TrackData
import com.kodex.sunny.utils.LOCATION_UPDATE_INTERVAL
import com.kodex.sunny.utils.PRIORITY
import com.kodex.sunny.utils.SettingsPreferenceManager
import com.kodex.sunny.utils.TRACK_COLOR
import com.kodex.sunny.utils.TRACK_LINE_WIDTH
import com.kodex.sunny.utils.TimerManager
import com.kodex.sunny.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private var timerManager: TimerManager,
    private val locationDataSharer: LocationDataSharer,
    private val settingsPreference: SettingsPreferenceManager,
    private val mainDb: MainDb
) : ViewModel() {
    val timerState = mutableStateOf("00:00:00:00")
    val locationDataFlow = locationDataSharer.locationDataFlow


    init {
        viewModelScope.launch {
            locationDataSharer.locationDataFlow.collect{locationData ->
                Log.d("LocTag_2", "LocationData: ${locationData.geoPoints}")
            }
        }
    }

    fun startTimer(startTimerInMillis: Long) {
        timerManager.startTimer(object : TimerTask() {
            override fun run() {
                timerState.value = TimeUtils.getTimerTime(startTimerInMillis)
            }
        })

    }

    fun insertTrack(trackData: TrackData) = viewModelScope.launch(Dispatchers.IO) {
        mainDb.trackDao.insertTrack(trackData.copy(
            time =timerState.value
        ))
    }

    fun stopTimer(){
        timerManager.stopTimer()
    }

    fun getLocationUpdateInterval(): String {
        return settingsPreference.getString(
            LOCATION_UPDATE_INTERVAL,
            "5000"
        )

    }
        fun getPriority(): String{
            return settingsPreference.getString(
                PRIORITY, "PRIORITY_HIGH_ACCURACY"
            )
        }

    fun getTrackLineWidth(): String {
        return settingsPreference.getString(
            TRACK_LINE_WIDTH,
            "5"
        )
    }
    fun getTraCKColor(): String{
        return settingsPreference.getString(
            TRACK_COLOR, Color.Red.value.toString()
        )
    }

}

/*
@Composable
fun MapScreen() {
    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MapScreen")
    }
}*/
