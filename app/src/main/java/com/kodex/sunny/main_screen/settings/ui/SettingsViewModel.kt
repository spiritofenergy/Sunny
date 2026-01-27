package com.kodex.gpstracker.main_screen.settings.ui

import androidx.annotation.InspectableProperty
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.kodex.sunny.utils.LOCATION_UPDATE_INTERVAL
import com.kodex.sunny.utils.PRIORITY
import com.kodex.sunny.utils.SettingsPreferenceManager
import com.kodex.sunny.utils.TRACK_COLOR
import com.kodex.sunny.utils.TRACK_LINE_WIDTH
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPreference: SettingsPreferenceManager
): ViewModel(){
    fun saveLocationUpdateInterval(interval: String){
        settingsPreference.saveString(
            LOCATION_UPDATE_INTERVAL,
            interval)
    }

    fun saveTrackLineWidth(width: String){
        settingsPreference.saveString(
            TRACK_LINE_WIDTH,
            width)
    }

    fun savePriority(priority: String){
        settingsPreference.saveString(
            PRIORITY,
            priority
        )
    }

    fun getPriority(): String{
        return settingsPreference.getString(
            PRIORITY, "PRIORITY_HIGH_ACCURACY"
        )
    }

    fun getLocationUpdateInterval(): String{
        return settingsPreference.getString(
            LOCATION_UPDATE_INTERVAL,
            "5000")

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

    fun saveColor(color: Color) {
        settingsPreference.saveString(
            TRACK_COLOR,
            color.value.toString()
        )
    }
}