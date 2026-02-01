package com.kodex.sunny.main_screen.button_bar.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.home.ui.getAllBooks

@Composable
fun ButtonMenu(
    selectedItemTitle: String,
    onItemClick: (String)-> Unit,
    //onHomeClick: () -> Unit = {}
) {
    val items = listOf(
        ButtonMenuItem.Home,
        ButtonMenuItem.Track,
        ButtonMenuItem.Login,
        ButtonMenuItem.Map,
        ButtonMenuItem.Settings,

    )
    NavigationBar(modifier = Modifier.fillMaxWidth()) {

        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItemTitle == item.title,
                onClick = {
                    onItemClick(item.title)
                    if (item.title == ButtonMenuItem.Home.title) {
                        Log.d("MyLog", "ButtonMenu: ${item.title}")

                   }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = "Home"
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }




    }
}