package com.kodex.sunny.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import javax.inject.Singleton

const val MAIN_PREF = "main_pref"
const val TRACK_LINE_WIDTH = "track_line_width"
const val PRIORITY = "priority"
const val TRACK_COLOR = "track_color"
const val LOCATION_UPDATE_INTERVAL = "location_update_interval"


@Singleton
class SettingsPreferenceManager (
    context: Context
){
    private var pref: SharedPreferences? = null

    init {
        pref = context.getSharedPreferences(MAIN_PREF, Context.MODE_PRIVATE)

    }
    fun saveString(key: String, value: String){
        pref?.edit()?.putString(
            key, value
        )?.apply()
    }

    fun getString(key: String, def: String): String{
        return pref?.getString(key, def)?: def
    }

}