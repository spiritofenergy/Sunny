package com.kodex.sunny.main_screen.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.addScreen.data.BookListItemUi
import com.kodex.sunny.addScreen.data.BookListItemUiReserve
import com.kodex.sunny.drawer_menu.DrawerBody
import com.kodex.sunny.drawer_menu.DrawerHeader
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.home.data.MainScreenDataObject
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    onBookEditClick: (Book) -> Unit,
    onAdminClick: () -> Unit,
    onAddBookClick: () -> Unit,

    ) {
    val driverState = rememberDrawerState(DrawerValue.Closed)
    val savedInstanceState = remember { mutableStateOf(ButtonMenuItem.Home.title) }
    val coroutineScope = rememberCoroutineScope()
    val bookListState = remember { mutableStateOf(emptyList<Book>()) }
    val isAdminState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllBooks(db) { books ->
            bookListState.value = books
        }
    }

    coroutineScope.launch { driverState.close() }

    ModalNavigationDrawer(
        drawerState = driverState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData.email)
                DrawerBody(
                    onAdmin = { isAdmin ->
                        isAdminState.value = isAdmin
                    },
                    onAddBookClick


                ) {
                    coroutineScope.launch { driverState.close() }
                }


            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {/* Меню навигации под шторкой драйвер меню
                ButtonMenu(
                    selectedItemTitle = "Home",
                    onItemClick = {}

                )*/
            }
        ) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                if (bookListState.value.isEmpty()) {
                    items(10) {
                        BookListItemUiReserve()
                    }
                } else
                    items(bookListState.value) { book ->
                        BookListItemUi(
                            isAdminState.value, book
                        ) { book ->
                            onBookEditClick(book)

                        }
                    }
            }
        }
    }
}

private fun getAllBooks(
    db: FirebaseFirestore,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener { task ->
            val books = task.toObjects(Book::class.java)
            onBooks(books)
            Log.d("MyLog", "fun getAllBooks: Success")
        }
        .addOnFailureListener { error ->
            Log.d("MyLog", "error getAllBooks: ${error}")

        }
}


