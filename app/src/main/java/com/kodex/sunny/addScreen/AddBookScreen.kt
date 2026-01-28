package com.kodex.sunny.addScreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kodex.sunny.R
import com.kodex.sunny.main_screen.home.data.MainScreenDataObject
import com.kodex.sunny.main_screen.login.ui.LoginButton
import com.kodex.sunny.main_screen.login.ui.RoundedCornerTextField
import com.kodex.sunny.ui.theme.BoxFilter
import com.kodex.sunny.utils.ImageUtils
import com.kodex.sunny.utils.toBitmap


@Composable
fun AddBookScreen(
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val prise = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ uri ->
        selectedImageUri.value = uri
    }
    Image(
        painter = painterResource(id = R.drawable.way),
        contentDescription = "Logo",
        modifier = Modifier.fillMaxSize(),
        alpha = 0.5f,
        contentScale = ContentScale.Crop

    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilter)
    )

    // Основной лист
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Фото
        Image(
            painter = rememberAsyncImagePainter(model = selectedImageUri.value),
            contentDescription = "Selected Image",
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            text = "Sunny",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Название:"
        ) {}
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = description.value,
            label = "Краткое описание:",
            singleLine = false,
            maxLines = 5
        ) {}

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = prise.value,
            label = "Цена:"
        ) {}

        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(text = "Выбрать фото") {
             imageLauncher.launch("image/*")
        }
        LoginButton(text = "Сохранить ") {
            // viewModel.uploadBook(navData.copy(imageUrl = imageBase64.value))

        }
    }
}

@Composable
@Preview
fun AddBookScreenPreview() {
    AddBookScreen()
}

