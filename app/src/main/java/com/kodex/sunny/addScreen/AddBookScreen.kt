package com.kodex.sunny.addScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kodex.bookmarket.navigation.NavRoutes
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.R
import com.kodex.sunny.addScreen.data.AddScreenObject
import com.kodex.sunny.main_screen.login.ui.LoginButton
import com.kodex.sunny.main_screen.login.ui.RoundedCornerTextField
import com.kodex.sunny.ui.theme.ButtonColorDark
import com.kodex.sunny.utils.ImageUtils.imageToBase64


@Composable
fun AddBookScreen(
    navData: NavRoutes.AddScreenObject = NavRoutes.AddScreenObject(),
    onSaved: () -> Unit = {},
) {
    val selectedCategory = remember { mutableStateOf(navData.category) }
    val title = remember { mutableStateOf(navData.title) }
    val description = remember { mutableStateOf(navData.description) }
    val price = remember { mutableStateOf(navData.price.toString()) }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val imageBitMap = remember {
        var bitMap: Bitmap? = null
        try {
            val base64Image = Base64.decode(navData.imageUrl, Base64.DEFAULT)
            bitMap = BitmapFactory.decodeByteArray(
                base64Image, 0,
                base64Image.size
            )
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
            Log.d("MyLog", "bitMap Error: ${e.message}")
        }
        Log.d("MyLog4", "bitMap: $bitMap")

        mutableStateOf(bitMap)
    }

    val cv = LocalContext.current.contentResolver

    val navImageUrl = remember { mutableStateOf(navData.imageUrl) }
    Log.d("MyLog3", "$navImageUrl")

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageBitMap.value = null
        selectedImageUri.value = uri
        Log.d("MyLog3", "$uri")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ButtonColorDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.way),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f,
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
                painter = rememberAsyncImagePainter(model = imageBitMap.value?: selectedImageUri.value),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Sunny",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(10.dp))
            RoundedCornerDropDownMenu(
                defCategory = selectedCategory.value,
                onOptionSelected = { selectedItem ->
                    selectedCategory.value = selectedItem
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            RoundedCornerTextField(
                text = title.value,
                label = "Название:"
            ) {
                title.value = it
            }
            Spacer(modifier = Modifier.height(10.dp))

            RoundedCornerTextField(
                text = description.value,
                label = "Краткое описание:",
                singleLine = false,
                maxLines = 5
            ) {
                description.value = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            RoundedCornerTextField(
                text = price.value,
                label = "Цена:"
            ) {
                price.value = it
            }

            Spacer(modifier = Modifier.height(10.dp))

            LoginButton(text = "Выбрать фото") {
                imageLauncher.launch("image/*")
            }

            LoginButton(text = "Save") {
                saveBookToFirestore(
                    firestore = FirebaseFirestore.getInstance(),
                    Book(
                        key = navData.key,
                        title = title.value,
                        description = description.value,
                        price = price.value.toInt(),
                        category = selectedCategory.value,
                        timestamp = System.currentTimeMillis(),
                        imageUrl = if (selectedImageUri.value != null){
                            imageToBase64(
                            selectedImageUri.value!!,
                             cv
                        )}
                        else {
                            navData.imageUrl
                        }
                ),
                        onSaved = {
                            onSaved()
                        },
                        onError = { error ->
                            Log.d("MyLog4", "Error: ${error}")
                        }
                    )
                    // viewModel.uploadBook(navData.copy(imageUrl = imageBase64.value))
            }
        }
    }
}

private fun saveBookImage(
    oldImageUrl: String,
    uri: Uri,
    storage: FirebaseStorage,
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: (String) -> Unit
) {
    val timeStamp = System.currentTimeMillis()
    val storageRef = if (oldImageUrl.isEmpty()) {
        storage.reference
        .child("book_images")
        .child("images/$timeStamp.jpg")
        }else{
        storage.getReferenceFromUrl(oldImageUrl)
    }
    val uploadTask = storageRef.putFile(uri)
    uploadTask.addOnSuccessListener {
        storageRef.downloadUrl.addOnSuccessListener { url ->
          /*  saveBookToFirestore(
                firestore,
                book.copy(imageUrl = url.toString()),
                onSaved = {
                    onSaved()
                },
                onError = {
                    onError(it)
                }
            )*/
        }
    }
}

private fun saveBookToFirestore(
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: (String) -> Unit
) {
    val db = firestore.collection("books")
    val key = book.key.ifEmpty { db.document().id }
    db.document(key)
        .set(book.copy(key = key))
        .addOnSuccessListener { onSaved() }
        .addOnFailureListener { onError(it.message ?: "Error") }
}

/*private fun imageToBase64(
    uri: Uri,
    contentResolver: ContentResolver
): String {
    val inputStream = contentResolver.openInputStream(uri)

    val bytes = inputStream?.readBytes()
    return bytes?.let {
        Base64.encodeToString(it, Base64.DEFAULT)
    } ?: ""
}*/

@Composable
@Preview
fun AddBookScreenPreview() {
    /*AddBookScreen {
        navController.popBackStack()
    }*/
}

