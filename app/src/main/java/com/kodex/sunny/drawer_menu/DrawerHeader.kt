package com.kodex.sunny.drawer_menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.ButtonColorDark
import com.kodex.sunny.ui.theme.ButtonColorTransparent


@Composable
fun DrawerHeader(
    email: String = "exampole@exampole.com"
) {
    Box (modifier = Modifier.fillMaxWidth()
        .background(ButtonColorDark)
        ){
        Column (
            Modifier.height(170.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ){
            Image(modifier = Modifier.size(90.dp),
                painter = painterResource(id = R.drawable.i),
                contentDescription = "ИСКРА"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp

            )
            Text(
                email,
                color = Color.Green,
                fontSize = 16.sp

            )
        }
    }
}
@Composable
@Preview
fun DrawerHeaderPreview() {
    DrawerHeader()
}