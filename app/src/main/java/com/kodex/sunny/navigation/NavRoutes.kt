package com.kodex.sunny.navigation

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
sealed class NavRoutes {

   // @SuppressLint("UnsafeOptInUsageError") constructor(
    @Serializable
    data class MainScreenDataObject  @SuppressLint("UnsafeOptInUsageError") constructor(
        val uid: String = "",
        val email: String = ""
    )
    @Serializable
    data class DetailsNavObject(
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val price: String = "",
        val imageUrl: String = "",
    )
        @Serializable
    object LoginScreenObject: NavRoutes()


    @Serializable
    data class AddScreenObject(
        val key: String = "",
        val title: String = "",
        val searchTitle: String = title.lowercase(),
        val description: String = "",
        val price: Int = 0,
        val category: String = "Выберите катерогию",
        val imageUrl: String = "",
        val author: String = "",
        val timestamp: Long = System.currentTimeMillis(),
        val isFaves: Boolean = false


    ): NavRoutes()
}
