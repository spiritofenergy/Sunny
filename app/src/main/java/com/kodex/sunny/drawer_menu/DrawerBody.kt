package com.kodex.sunny.drawer_menu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.ButtonColorDark
import com.kodex.sunny.ui.theme.DarkTransparentBlue
import com.kodex.sunny.ui.theme.GrayLite


@Composable
fun DrawerBody(
    // viewModel: MainScreenViewModel = hiltViewModel(),
    onFavesClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onMealsClick: () -> Unit = {},
    onSwimmingClick: () -> Unit = {},
    onEntertainmentClick: () -> Unit = {},
    onTreatmentClick: () -> Unit = {},
    onExcursionsClick: () -> Unit = {},
    onBookingClick: () -> Unit = {},
    onAdmin: (Boolean) -> Unit,
    onAdminClick: () -> Unit = {},
    onAddBookClick: () -> Unit = {},
    onCategoryClickInt: (Int) -> Unit = {},
    onCategoryClickString: (String) -> Unit = {}

) {
    val categoryList = stringArrayResource(id = R.array.category_arrays)
    val isAdminState = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        isAdmin { isAdmin ->
            isAdminState.value = isAdmin
            onAdmin(isAdmin)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ButtonColorDark)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.way),
            contentDescription = "",
            alpha = 0.2f,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(GrayLite)
            )
            DrawerListItem(title = categoryList[7]) {
                onFavesClick()
            }
            DrawerListItem(title = categoryList[0]) {
                onHomeClick()
            }
            DrawerListItem(title = categoryList[1]) {
                onMealsClick()
            }
            DrawerListItem(title = categoryList[2]) {
                onSwimmingClick()
            }
            DrawerListItem(title = categoryList[3]) {
                onEntertainmentClick()
            }
            DrawerListItem(title = categoryList[4]) {
                onTreatmentClick()
            }
            DrawerListItem(title = categoryList[5]) {
                onExcursionsClick()
            }
            DrawerListItem(title = categoryList[6]) {
                onBookingClick()
            }
          /*  LazyColumn(Modifier.fillMaxWidth()) {
                itemsIndexed(categoryList){index, title->
                    DrawerListItem(title) {
                        onCategoryClickInt(index)
                    }
                }
            }*/

            if (isAdminState.value) Button(
                onClick = {
                    onAdminClick()
                    isAdmin { }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkTransparentBlue
                )
            ) {
                Text(text = "Admin panel")
            }
            Button(
                onClick = {
                    isAdmin { }
                    onAdminClick()
                    onAddBookClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkTransparentBlue
                )
            ) {
                Text(text = "Добавить")
            }

        }
    }
}

fun isAdmin(onAdmin: (Boolean) -> Unit) {
    val uid = Firebase.auth.currentUser!!.uid
    Firebase.firestore.collection("admin")
        .document(uid).get().addOnSuccessListener {
            onAdmin(it.get("isAdmin") as Boolean)
            Log.d("MyLog", "isAdmin: ${it.get("isAdmin")}")
        }
}

@Composable
@Preview
fun DrawerBodyPreview() {
    DrawerBody(

        onAdmin = {}
    )
}

