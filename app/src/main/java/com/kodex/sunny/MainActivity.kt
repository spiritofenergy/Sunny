package com.kodex.sunny

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kodex.gpstracker.main_screen.settings.ui.SettingScreen
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.button_bar.ui.ButtonMenu
import com.kodex.sunny.main_screen.home.data.HomeNavData
import com.kodex.sunny.main_screen.home.ui.HomeScreen
import com.kodex.sunny.main_screen.login.data.LoginNavData
import com.kodex.sunny.main_screen.login.ui.LoginScreen
import com.kodex.sunny.main_screen.main.MainScreen
import com.kodex.sunny.main_screen.map.data.MapNavData
import com.kodex.sunny.main_screen.map.ui.MapScreen
import com.kodex.sunny.main_screen.settings.data.SettingNavData
import com.kodex.sunny.main_screen.tracker.data.TrackerNavData
import com.kodex.sunny.main_screen.tracker.ui.TrackerScreen
import com.kodex.sunny.ui.theme.SunnyTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setUpOSM(this)
        setContent {
            val navController = rememberNavController()
            val savedInstanceState = remember{
                mutableStateOf(ButtonMenuItem.Home.title)
            }
            SunnyTheme {

                    Scaffold(modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            ButtonMenu(savedInstanceState.value){savedInstanceTitle ->

                                savedInstanceState.value = savedInstanceTitle
                                when(savedInstanceTitle){
                                    ButtonMenuItem.Home.title -> navController.navigate(HomeNavData)
                                    ButtonMenuItem.Track.title -> navController.navigate(TrackerNavData)
                                    ButtonMenuItem.Login.title -> navController.navigate(LoginNavData)
                                    ButtonMenuItem.Settings.title -> navController.navigate(SettingNavData)
                                    ButtonMenuItem.Map.title -> navController.navigate(MapNavData)
                                }

                            }
                        }) {
                            innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = LoginNavData,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<HomeNavData> {
                                MainScreen()
                              //  navController.navigate(SettingNavData )
                            }

                            composable<SettingNavData> {
                                SettingScreen()
                            }

                            composable<LoginNavData> {
                                LoginScreen()
                            }


                            composable<TrackerNavData> {
                                TrackerScreen()
                            }
                            composable<MapNavData> {
                                MapScreen()
                            }
                        }
                    }
            }
        }
    }
}


private fun setUpOSM(context: Context){
    val config = Configuration.getInstance()
    config.load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    config.userAgentValue = context.packageName
}
