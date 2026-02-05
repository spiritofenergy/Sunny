package com.kodex.sunny.main_screen.details.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kodex.bookmarket.navigation.NavRoutes

@Preview(showBackground = true)
@Composable
fun DetailScreen(
    navObject: NavRoutes.DetailsNavObject = NavRoutes.DetailsNavObject(),

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var bitmap: Bitmap? = null
        try {
            val base64Image = Base64.decode(navObject.imageUrl, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(
                base64Image, 0,
                base64Image.size
            )
        }catch (e: IllegalArgumentException){

        }

        Column (modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier.fillMaxWidth()){

                AsyncImage(
                    model = bitmap,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .padding(top = 10.dp, bottom = 20.dp)
                        .height(190.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.FillHeight
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column (modifier = Modifier.fillMaxWidth()
                    .height(190.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Категория",
                        color = Color.Gray)
                    Text(text = navObject.category,
                        fontWeight = FontWeight.Bold)
                    Text(text = "Автор",
                        color = Color.Gray)
                    Text(text = "А С Пушкин",
                        fontWeight = FontWeight.Bold)
                    Text(text = "Дата печати",
                        color = Color.Gray)
                    Text(text = "12-02- 2025",
                        fontWeight = FontWeight.Bold)
                    Text(
                        text = "Цена: ${navObject.price} руб",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(26.dp))
            Text(
                text = navObject.title,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp)
            Text(
                text = navObject.description,
                fontSize = 16.sp
            )
            Row (modifier = Modifier.fillMaxWidth(),
            ){
                Button(modifier = Modifier.fillMaxWidth().weight(0.7F), onClick = {

                }) {
                    Text(text = "${navObject.price} Bay Now")
                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = {

                }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = ""
                    )
                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = {

                }) {
                    Icons.Default.ShoppingCart

                }
            }
        }
    }
}