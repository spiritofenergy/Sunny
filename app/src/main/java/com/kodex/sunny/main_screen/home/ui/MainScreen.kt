package com.kodex.sunny.main_screen.home.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kodex.bookmarket.navigation.NavRoutes
import com.kodex.spark.ui.addScreen.data.Favorite
import com.kodex.sunny.R
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.addScreen.data.BookListItemUi
import com.kodex.sunny.addScreen.data.BookListItemUiReserve
import com.kodex.sunny.drawer_menu.DrawerBody
import com.kodex.sunny.drawer_menu.DrawerHeader
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    navData: NavRoutes.MainScreenDataObject,
    onBookEditClick: (Book) -> Unit,
    onAddBookClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBookClick: (Book) -> Unit,
    onAdminClick: () -> Unit,

    ) {
    val categoryList = stringArrayResource(id = R.array.category_arrays)
    val driverState = rememberDrawerState(DrawerValue.Closed)
    val savedInstanceState = remember { mutableStateOf(ButtonMenuItem.Home.title) }
    val coroutineScope = rememberCoroutineScope()
    val bookListState = remember { mutableStateOf(emptyList<Book>()) }
    val isAdminState = remember { mutableStateOf(false) }
    val isFavListEmptyState = remember { mutableStateOf(false) }
    val selectedBottomItemState = remember { mutableStateOf(ButtonMenuItem.Home.title) }

    val db = remember { Firebase.firestore }
    LaunchedEffect(Unit) {
        getAllFavesIds(db, navData.uid) { faves ->
            getAllBooks(db, faves) { books ->
                bookListState.value = books
            }
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
                    onFavesClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllFavesBooks(db, faves) { books ->
                                isFavListEmptyState.value = books.isEmpty()
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },
                    onHomeClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooks(db, faves) { books ->
                                bookListState.value = books
                                isFavListEmptyState.value = books.isEmpty()
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onMealsClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[1]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onSwimmingClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[2]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onEntertainmentClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[3]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onTreatmentClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[4]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onExcursionsClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[5]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onBookingClick = {
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, categoryList[6]) { books ->
                                bookListState.value = books
                                coroutineScope.launch { driverState.close() }
                            }
                        }
                    },

                    onAdmin = { isAdmin ->
                        isAdminState.value = isAdmin
                    },
                    onAddBookClick,
                    onCategoryClickString = { category ->
                        getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooksFromCategory(db, faves, category) { books ->
                                bookListState.value = books
                            }
                        }
                    }
                ) /*{
                    coroutineScope.launch { driverState.close() }
                }*/
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
            if (isFavListEmptyState.value)
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    BookListItemUiReserve()
                }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

               /* if (bookListState.value.isEmpty()) {
                    items(10) {
                        BookListItemUiReserve()
                    }
                } else*/
                    items(bookListState.value) { book ->
                        Log.d("MyLog", "MainScreen: ${bookListState}")
                        BookListItemUi(
                            isAdminState.value,
                            book,
                            onBookClick = {bk->
                                onBookClick(bk)
                            },
                            onEditClick = {
                                onBookEditClick(book)
                            },
                            onFavesClick = {
                                bookListState.value = bookListState.value.map { bk ->
                                    if (bk.key == book.key) {
                                        onFaves(
                                            db,
                                            navData.uid,
                                            Favorite(book.key),
                                            !bk.isFaves,
                                        )
                                        bk.copy(isFaves = !bk.isFaves)
                                    } else {
                                        bk
                                    }
                                }
                                if (selectedBottomItemState.value == ButtonMenuItem.Favorite.title)
                                    bookListState.value =
                                        bookListState.value.sortedBy { it.isFaves }
                            }
                        )
                    }
            }
        }
    }
}








