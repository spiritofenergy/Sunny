package com.kodex.sunny.main_screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodex.sunny.drawer_menu.DrawerBody
import com.kodex.sunny.drawer_menu.DrawerHeader
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.button_bar.ui.ButtonMenu


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val driverState = rememberDrawerState(DrawerValue.Open)
    val savedInstanceState = remember{
        mutableStateOf(ButtonMenuItem.Home.title)
    }
    ModalNavigationDrawer(
        drawerState = driverState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader()
                DrawerBody(
                    onAdmin = {

                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {/* Меню навигации под шторкой драйвер меню
                ButtonMenu(
                    selectedItemTitle = "Home",
                    onItemClick = {}

                )*/
            }
        ) {

        }
    }
}
@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}

