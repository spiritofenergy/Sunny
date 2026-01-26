package com.kodex.sunny.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kodex.sunny.ui.theme.PurpleGrey80

@Composable
fun BottomMenu(
    selectedItem: Int,
    onFavesClick: ()-> Unit = {},
    onHomeClick: ()-> Unit = {},
    onParkClick: ()-> Unit = {},
    onSunnyClick: ()-> Unit = {},
    onFoodClick: ()-> Unit = {},
    onHealthClick: ()-> Unit = {},
    onBookingClick: ()-> Unit = {}
) {
    val items = listOf(
        BottomMenuItem.Home,
       // BottomMenuItem.Faves,
        BottomMenuItem.Park,
        BottomMenuItem.Sunny,
        BottomMenuItem.Food,
        BottomMenuItem.Health,
    )
    NavigationBar (
        containerColor = PurpleGrey80
    ){
        items.forEach{ item->
           NavigationBarItem(
               selected = selectedItem == item.titleId,
               onClick = {
                   // сравниваем значение нажатого элемента и запускаем нужную функцию
                   when(item.titleId){
                       BottomMenuItem.Home.titleId -> onHomeClick()
                       BottomMenuItem.Faves.titleId -> onFavesClick()
                       BottomMenuItem.Park.titleId -> onParkClick()
                       BottomMenuItem.Sunny.titleId -> onSunnyClick()
                       BottomMenuItem.Food.titleId -> onFoodClick()
                       BottomMenuItem.Health.titleId -> onHealthClick()
                       BottomMenuItem.Booking.titleId -> onBookingClick()
                   }
               },
               icon = {
                   Icon(
                       painter = painterResource(id = item.iconId),
                       contentDescription = null)
                      },


               label = {
                   Text(text = stringResource(item.titleId))
               },
/*
               colors = NavigationDrawerItemDefaults.colors(
                   selectedIconColor = DarkBlue,
                   selectedTextColor = DarkBlue,
                   unselectedIconColor = Color.Gray,
                   unselectedTextColor = Color.Gray

               )*/
           )
        }
    }
}