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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.play.core.integrity.v
import com.kodex.bookmarket.navigation.NavRoutes
import com.kodex.sunny.R
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.addScreen.data.BookListItemUi
import com.kodex.sunny.addScreen.data.BookListItemUiReserve
import com.kodex.sunny.custom_ui.MyDialog
import com.kodex.sunny.drawer_menu.DrawerBody
import com.kodex.sunny.drawer_menu.DrawerHeader
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
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
    val isAdminState = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (viewModel.booksListState.value.isEmpty()) {
            viewModel.getAllBooks()
            Log.d("MyLog0", "${viewModel.booksListState.value} ")
        }else{
            viewModel.selectedBottomItemState.value = savedInstanceState.value
            Log.d("MyLog0", "$navData: пусто ")

        }
        Log.d("MyLog1", "MainScreen: ${viewModel.booksListState}")
        Log.d("MyLog2", "$navData: ")

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
                            viewModel.getAllFavesBook(
                                isListEmpty = {isEmpty->
                                    viewModel.isFavesListEmptyState.value = isEmpty
                                }
                            )
                                coroutineScope.launch { driverState.close() }
                    },
                    onHomeClick = {
                        viewModel.selectedBottomItemState.value = ButtonMenuItem.Home.title
                        viewModel.getAllBooks()
                        coroutineScope.launch { driverState.close() }
                    },

                    onMealsClick = {
                       viewModel.getAllBooksFromCategory(categoryList[1])
                        coroutineScope.launch { driverState.close() }

                    },

                    onSwimmingClick = {
                      viewModel.getAllBooksFromCategory(categoryList[2])
                        coroutineScope.launch { driverState.close() }
                    },

                    onEntertainmentClick = {
                       viewModel.getAllBooksFromCategory(categoryList[3])
                        coroutineScope.launch { driverState.close() }
                    },

                    onTreatmentClick = {
                       viewModel.getAllBooksFromCategory(categoryList[4])
                        coroutineScope.launch { driverState.close() }
                    },

                    onExcursionsClick = {
                       viewModel.getAllBooksFromCategory(categoryList[5])
                        coroutineScope.launch { driverState.close() }
                    },

                    onBookingClick = {
                      viewModel.getAllBooksFromCategory(categoryList[6])
                        coroutineScope.launch { driverState.close() }
                    },

                    onAdmin = { isAdmin ->
                        isAdminState.value = isAdmin
                    },
                    onAddBookClick,
                    onCategoryClickString = { category ->
                        viewModel.getAllBooksFromCategory(category)
                        coroutineScope.launch { driverState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
           /* bottomBar = {
                ButtonMenu(
                    selectedItemTitle = "Home",
                    onItemClick = {
                        viewModel.selectedBottomItemState.value = ButtonMenuItem.Home.title
                        viewModel.getAllBooks()
                    }
                )

            }*/
        ) { paddingValues ->

          if (showDeleteDialog.value){
              MyDialog(
                  showDialog = showDeleteDialog.value,
                  onDismiss = { showDeleteDialog.value = false},
                  onConfirm = { showDeleteDialog.value = false},
                  title = "Delete this Book?",
                  massage = "Are you sure you want to delete this book?",
                  confirmButtonText = "Delete Book"
              )
          }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                    items(viewModel.booksListState.value) { book ->
                        Log.d("MyLog2", "MainScreen: ${viewModel.booksListState}")
                        BookListItemUi(
                            isAdminState.value,
                            book,
                            onBookClick = {bk->
                                onBookClick(bk)
                            },
                            onEditClick = {
                                onBookEditClick(it)
                            },
                            onFavesClick = {
                                    viewModel.onFavesClick(book, viewModel.selectedBottomItemState.value)
                            }
                        )
                    }
            }
        }
    }
}









