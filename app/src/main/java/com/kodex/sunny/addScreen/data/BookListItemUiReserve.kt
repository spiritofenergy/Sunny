package com.kodex.sunny.addScreen.data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kodex.sunny.R

@Composable
fun BookListItemUiReserve (){

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = R.drawable.kuch1,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),

            )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Title",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Description",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = "Руководитель и координатор поисковой организации «ПримПоиск» Кристина Вульферт заявила, что в настоящий момент считается, что, вероятнее всего, причиной пропажи семьи Усольцевых в Красноярском крае стал несчастный случай IP-адрес — это уникальный идентификатор устройства в интернете. С помощью сервиса проверки IP можно получить информацию об адресе, интернет-провайдере.",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row() {


            Text(
                text = 58.toString(),
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 16.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )


            Text(
                text = "p",
                modifier = Modifier . padding (start = 10.dp),
                fontSize = 16.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreviewReserve() {
     BookListItemUiReserve()
}
