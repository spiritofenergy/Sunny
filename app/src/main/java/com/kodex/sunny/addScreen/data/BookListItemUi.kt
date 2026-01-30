package com.kodex.sunny.addScreen.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kodex.spark.ui.addScreen.data.Book
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.Orange

@Composable
fun BookListItemUi(
    book: Book
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)

    ) {
        var bitMap: Bitmap? = null
        try {
            val base64Image = Base64.decode(book.imageUrl, Base64.DEFAULT)
            bitMap = BitmapFactory.decodeByteArray(
                base64Image, 0,
                base64Image.size
            )
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
            Log.d("MyLog", "bitMap Error: ${e.message}")
        }


        AsyncImage(
            model = bitMap,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),

            )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = book.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = book.description,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = book.price.toString(),
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    // BookListItemUi()
}
