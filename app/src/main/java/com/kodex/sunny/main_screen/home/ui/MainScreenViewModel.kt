package com.kodex.sunny.main_screen.home.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.main_screen.button_bar.data.ButtonMenuItem
import com.kodex.sunny.utils.FirebaseManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel@Inject constructor(
    private val firebaseManager: FirebaseManager
): ViewModel() {

    val booksListState = mutableStateOf(emptyList<Book>())
    val selectedBottomItemState =  mutableStateOf(ButtonMenuItem.Home.title)
    val isFavesListEmptyState =   mutableStateOf(false)
    val savedInstanceState = mutableStateOf(ButtonMenuItem.Home.title)

    fun getAllBooks(){
        firebaseManager.getAllBooks { books ->
            booksListState.value = books
            isFavesListEmptyState.value = books.isEmpty()
        }
    }
    fun getAllFavesBook(isListEmpty: (Boolean) -> Unit){
        firebaseManager.getAllFavesBooks { books ->
            booksListState.value = books
            isListEmpty(books.isEmpty())

        }
    }
    fun getAllBooksFromCategory(category: String) {
        if (category == "All"){
            getAllBooks()
        return
    }
        firebaseManager.getAllBooksFromCategory(category) { books ->
            booksListState.value = books
        }
    }
    fun onFavesClick(book: Book, isFavesState: String){
        val booksList = firebaseManager.changeFavState(booksListState.value, book)
        booksListState.value = if (isFavesState == ButtonMenuItem.Favorite.title) {
            booksList.filter { it.isFaves }
        }else {
            booksList
        }
        isFavesListEmptyState.value = booksListState.value.isEmpty()
    }
}
