package com.kodex.spark.addScreen

import android.net.Uri
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodex.sunny.addScreen.data.AddScreenObject
import com.kodex.sunny.addScreen.data.Book
import com.kodex.sunny.mainScreen.MainScreenViewModel
import com.kodex.sunny.utils.Categories
import com.kodex.sunny.utils.firebase.FireStoreManagerPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
   // private val navDataMain: MainScreenDataObject,
    private val firebaseManager: FireStoreManagerPaging,
) : ViewModel() {
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val prise = mutableStateOf("")
   // val author = mutableStateOf(navDataMain.email)
    val timestamp = mutableStateOf("")
    val selectedCategory = mutableIntStateOf(Categories.PARK)
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val showLoadingIndicator =  mutableStateOf(false)

    private val _uiState = MutableSharedFlow<MainScreenViewModel.MainUiState>()
    val uiState = _uiState.asSharedFlow()

    private fun sendUiState(state: MainScreenViewModel.MainUiState) = viewModelScope.launch {
        _uiState.emit(state)
    }

    fun setDefaultData(navData: AddScreenObject
                     // , navDataMain: MainScreenDataObject
    ) {
        title.value = navData.title
        description.value = navData.description
        prise.value = navData.price.toString()
       // author.value = navDataMain.email
        timestamp.value = navData.timestamp.toString()
        selectedCategory.intValue = navData.categoryIndex


    }

    fun uploadBook(
        navData: AddScreenObject,
    ) {
        sendUiState(MainScreenViewModel.MainUiState.Loading)
        val book = Book(
            key = navData.key,
            title = title.value,
            description = description.value,
            price = prise.value.toInt(),
            author = navData.key,
            timestamp = currentTimeMillis(),
            categoryIndex = selectedCategory.intValue,
            imageUrl = navData.imageUrl
        )
        firebaseManager.saveBookImage(
            oldImageUrl = navData.imageUrl,
            uri = selectedImageUri.value,
            book = book,
            onSaved = {
                sendUiState(MainScreenViewModel.MainUiState.Success)
            },
            onError = { massage ->
                sendUiState(MainScreenViewModel.MainUiState.Error(massage))
            }
        )
    }
}