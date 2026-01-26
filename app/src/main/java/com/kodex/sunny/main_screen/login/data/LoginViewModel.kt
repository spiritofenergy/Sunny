package com.kodex.sunny.main_screen.login.data

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.kodex.sunny.main_screen.home.data.HomeScreenDataObject
import com.kodex.sunny.utils.AuthManager
import com.kodex.sunny.utils.Categories
import com.kodex.sunny.utils.firebase.StoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val storeManager: StoreManager,
) : ViewModel() {
    val selectedCategory = mutableIntStateOf(Categories.PARK)
    val currentUser = mutableStateOf<FirebaseUser?>(null)
    val showResetPasswordDialog = mutableStateOf(false)
    val successState = mutableStateOf("Welcome")
    val errorState = mutableStateOf("")
    val emailState = mutableStateOf("nillsimon24@gmail.com")
    val passwordState = mutableStateOf("test24")
    val resetPasswordState = mutableStateOf(false)

    fun signIn(
        onSignInSuccess: (HomeScreenDataObject) -> Unit,
    ) {
        errorState.value = ""
        authManager.signIn(
            emailState.value,
            passwordState.value,
            onSignInSuccess = { navData ->
                onSignInSuccess(navData)
            },
            onSignInFailure = { errorMassage ->
                errorState.value = errorMassage
            }
        )
    }
    fun getEmail(){
        emailState.value = storeManager.getString(StoreManager.EMAIL_KEY, "nillsimon24@gmail.com")
    }

    fun saveLastEmail(){
        storeManager.saveString(StoreManager.EMAIL_KEY, emailState.value)
    }

    fun signUp(
        onSignUpSuccess: (HomeScreenDataObject) -> Unit,
    ) {
        errorState.value = ""
        if (resetPasswordState.value) {
            authManager.resetPassword(
                emailState.value,
                onResetPasswordSuccess = {
                    resetPasswordState.value = false
                    showResetPasswordDialog.value = true
                    Log.d("MyLog1", "SendPass_1")
                },
                onResetPasswordFailure = { errorMessage ->
                    errorState.value = errorMessage
                }
            )
            return
        }
        authManager.signUp(
            emailState.value,
            passwordState.value,
            onSignUpSuccess = { navData ->
                onSignUpSuccess(navData)
                Log.d("MyLog", "SendPass_2")
            },
            onSignUpFailure = { errorMassage ->
                errorState.value = errorMassage
            }
        )
    }

    // fun getAccountClosed(){
    fun getAccountState() {
        currentUser.value = authManager.getCurrentUser()
    }

    fun signOut() {
        authManager.signOut()
        currentUser.value = null
    }
}