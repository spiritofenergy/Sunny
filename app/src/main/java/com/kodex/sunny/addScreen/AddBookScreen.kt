package com.kodex.sunny.addScreen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.kodex.spark.addScreen.AddBookViewModel
import com.kodex.sunny.R
import com.kodex.sunny.addScreen.data.AddScreenObject
import com.kodex.sunny.mainScreen.MainScreenViewModel
import com.kodex.sunny.main_screen.login.ui.LoginButton
import com.kodex.sunny.main_screen.login.ui.RoundedCornerTextField
import com.kodex.sunny.ui.theme.BoxFilter
import com.kodex.sunny.utils.ImageUtils
import com.kodex.sunny.utils.firebase.IS_BASE_64
import com.kodex.sunny.utils.toBitmap


@Composable
fun AddBookScreen(
    navData: AddScreenObject = AddScreenObject(),
   // navDataMain: MainScreenDataObject = MainScreenDataObject(),
    onSaved: () -> Unit = {},
    viewModel: AddBookViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    var selectedCategory = remember {
        mutableIntStateOf(navData.categoryIndex)
    }
    var navImageUrl = remember {
        mutableStateOf(navData.imageUrl)
    }
    val imageBase64 =  remember {
        mutableStateOf(if (IS_BASE_64) navData.imageUrl else "")
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
          if (IS_BASE_64) {
             imageBase64.value = uri?.let {
                ImageUtils.imageToBase64(uri, context.contentResolver)
            } ?: ""
        } else {
            navImageUrl.value = ""
            viewModel.selectedImageUri.value = uri
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setDefaultData(navData)
        viewModel.uiState.collect { state ->
            when (state) {
                is MainScreenViewModel.MainUiState.Loading -> {
                    viewModel.showLoadingIndicator.value = true
                }

                is MainScreenViewModel.MainUiState.Success -> {
                    onSaved()
                }

                is MainScreenViewModel.MainUiState.Error -> {
                   viewModel.showLoadingIndicator.value = false
                    Toast.makeText(context, "Error: ${state.massage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //фон
    Image(
        painter = painterResource(id = R.drawable.i),
        contentDescription = "Logo",
        modifier = Modifier.fillMaxSize(),
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
            painter = rememberAsyncImagePainter(
                model = if(imageBase64.value.isNotEmpty()) {
                    imageBase64.value.toBitmap()
                } else {
                    navImageUrl.value.ifEmpty {viewModel.selectedImageUri.value }
               }
            ),
            contentDescription = "",
            modifier = Modifier
                .height(300.dp)
                .width(500.dp)


        )
        Text(
            text = "ИСКРА",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(20.dp))

        RoundedCornerDropDownMenu(viewModel.selectedCategory.intValue) { selectedItemIndex ->
            imageLauncher
            viewModel.selectedCategory.intValue = selectedItemIndex
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = viewModel.title.value,
            label = "Название:"
        ) {
            viewModel.title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = viewModel.description.value,
            label = "Краткое описание:",
            singleLine = false,
            maxLines = 5
        ) {
            viewModel.description.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = viewModel.prise.value,
            label = "Цена:"
        ) {
            viewModel.prise.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(text = "Выбрать фото") {
            imageLauncher.launch("image/*")
        }
        LoginButton(text = "Сохранить ") {
            viewModel.uploadBook(navData.copy(imageUrl = imageBase64.value))

        }
    }
}


