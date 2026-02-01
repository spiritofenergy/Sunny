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
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.kodex.spark.ui.addScreen.data.Favorite
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.addScreen.data.BookListItemUi
import com.kodex.sunny.addScreen.data.BookListItemUiReserve
import com.kodex.sunny.drawer_menu.DrawerBody
import com.kodex.sunny.drawer_menu.DrawerHeader
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.main_screen.home.data.MainScreenDataObject
import kotlinx.coroutines.delay
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
                                bookListState.value = books
                                    coroutineScope.launch { driverState.close() }
                            }
                        }
                    },
                    onHomeClick = {
                      getAllFavesIds(db, navData.uid) { faves ->
                            getAllBooks(db, faves) { books ->
                                bookListState.value = books
                            }
                        }
                    },

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
                columns = GridCells.Fixed(2),
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
                            isAdminState.value,
                            book,
                            onEditClick = {
                                onBookEditClick(book)
                            },
                            onFavesClick = {
                                bookListState.value = bookListState.value.map {bk ->
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
                            }
                        )
                    }
            }
        }
    }
}

 fun getAllBooks(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener { task ->
            val booksList = task.toObjects(Book::class.java).map {
                if (idsList.contains(it.key)) {
                    it.copy(isFaves = true)
                } else {
                    it
                }
            }
            onBooks(booksList)
            Log.d("MyLog", "fun getAllBooks: Success")
        }
        .addOnFailureListener { error ->
            Log.d("MyLog", "error getAllBooks: ${error}")

        }
}
private fun getAllFavesBooks(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .whereIn(FieldPath.documentId(), idsList)
        .get()
        .addOnSuccessListener { task ->
            val booksList = task.toObjects(Book::class.java).map {
                if (idsList.contains(it.key)) {
                    it.copy(isFaves = true)
                } else {
                    it
                }
            }
            onBooks(booksList)
        }
        .addOnFailureListener { error ->
            Log.d("MyLog", "error getAllBooks: ${error}")

        }
}
 fun getAllFavesIds(
    db: FirebaseFirestore,
    uid: String,
    onFaves: (List<String>) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favorites")
        .get()
        .addOnSuccessListener { task ->
            val idsList = task.toObjects(Favorite::class.java)
            val keysList = arrayListOf<String>()
            idsList.forEach {
                keysList.add(it.key)
            }
            onFaves(keysList)
        }
        .addOnFailureListener {

        }
}

private fun onFaves(
    db: FirebaseFirestore,
    uid: String,
    favorite: Favorite,
    isFaves: Boolean,
    ){
    if (isFaves) {
        db.collection(
            "users"
        ).document(uid)
            .collection("favorites")
            .document(favorite.key)
            .set(favorite)

    }else{
        db.collection(
            "users"
        ).document(uid)
            .collection("favorites")
            .document(favorite.key)
            .delete()
    }
}

