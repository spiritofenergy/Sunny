package com.kodex.sunny.main_screen.button_bar.data

 import com.kodex.sunny.R

sealed class ButtonMenuItem(val title: String, val iconId: Int) {
     data object Home: ButtonMenuItem("Home", R.drawable.ic_home)
     data object Settings: ButtonMenuItem("Setting", R.drawable.ic_settings)
     data object Login: ButtonMenuItem("Login", R.drawable.ic_login)
     data object Map: ButtonMenuItem("Map", R.drawable.my_location)
     data object Track: ButtonMenuItem("Track", R.drawable.ic_tracker)
}