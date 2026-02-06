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
import androidx.navigation.toRoute
import com.kodex.bookmarket.navigation.NavRoutes
import com.kodex.gpstracker.main_screen.settings.ui.SettingScreen
import com.kodex.sunny.addScreen.AddBookScreen
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.button_bar.ui.ButtonMenuMap
import com.kodex.sunny.main_screen.details.ui.DetailScreen
import com.kodex.sunny.main_screen.login.ui.LoginScreen
import com.kodex.sunny.main_screen.home.ui.MainScreen
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
            val savedInstanceState = remember {
                mutableStateOf(ButtonMenuItem.Home.title)
            }
            SunnyTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        ButtonMenuMap(
                            selectedItemTitle = savedInstanceState.value) { savedInstanceTitle ->
                            savedInstanceState.value = savedInstanceTitle
                            when (savedInstanceTitle) {
                                ButtonMenuItem.Home.title -> navController.navigate(NavRoutes.MainScreenDataObject(
                                    uid = "uid",
                                    email = "email"
                                ))
                                ButtonMenuItem.Track.title -> navController.navigate(TrackerNavData)
                                ButtonMenuItem.Login.title -> navController.navigate(NavRoutes.LoginScreenObject)
                                ButtonMenuItem.Settings.title -> navController.navigate(SettingNavData)
                                ButtonMenuItem.Map.title -> navController.navigate(NavRoutes.MapScreenObject)
                            }
                        }
                    }

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.LoginScreenObject,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable<NavRoutes.MainScreenDataObject> { navEntry ->
                            val navData = navEntry.toRoute<NavRoutes.MainScreenDataObject>()
                            MainScreen(
                                navData = navData,
                                onBookClick = { bk ->
                                    navController.navigate(NavRoutes.DetailsNavObject(
                                        title = bk.title,
                                        description = bk.description,
                                        price = bk.price,
                                        category = bk.category,
                                        imageUrl = bk.imageUrl
                                    ))
                                },
                                onBookEditClick ={ book ->
                                    navController.navigate(
                                        NavRoutes.AddScreenObject(
                                        key = book.key,
                                        title = book.title,
                                        description = book.description,
                                        price = book.price.toString(),
                                        category = book.category,
                                        imageUrl = book.imageUrl,
                                        author = book.author,
                                        timestamp = book.timestamp,
                                        isFaves = book.isFaves,
                                    ))
                                },
                                onAdminClick = {
                                    navController.navigate(NavRoutes.AddScreenObject())
                                },
                                onAddBookClick = {
                                    navController.navigate(NavRoutes.AddScreenObject())
                                },
                                onHomeClick = {
                                    navController.navigate(NavRoutes.MainScreenDataObject)
                                }
                            )
                        }
                        composable<NavRoutes.AddScreenObject> { navEntry ->
                            val navData = navEntry.toRoute<NavRoutes.AddScreenObject>()
                            AddBookScreen(navData){
                                navController.popBackStack()
                            }

                        }
                        composable<NavRoutes.DetailsNavObject> { navEntry ->
                            val navData = navEntry.toRoute<NavRoutes.DetailsNavObject>()
                            DetailScreen(navData)

                        }

                        composable<SettingNavData> {
                            SettingScreen()
                        }

                        composable<NavRoutes.LoginScreenObject> {
                            LoginScreen { navData ->
                                navController.navigate(navData)
                            }
                        }

                        composable<TrackerNavData> {
                            TrackerScreen()
                        }
                        composable<NavRoutes.MapScreenObject> {
                            MapScreen()
                        }
                    }
                }
            }
        }
    }
}


private fun setUpOSM(context: Context) {
    val config = Configuration.getInstance()
    config.load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    config.userAgentValue = context.packageName
}
