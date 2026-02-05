package com.kodex.bookmarket.navigation

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
sealed class NavRoutes {

    @Serializable
    data class MainScreenDataObject @SuppressLint("UnsafeOptInUsageError") constructor(
        val uid: String = "",
        val email: String = "",
        val id: Int? = null,
        val key: String = "",
        val title: String = "",
        val searchTitle: String = title.lowercase(),
        val description: String = "",
        val price: Int = 0,
        val category: String = "",
        val imageUrl: String = "",
        val author: String = "",
        val timestamp: Long = System.currentTimeMillis(),
        val isFaves: Boolean = false,
        val ratingsList: List<Int> = emptyList()
    )
    @Serializable
    data class DetailsNavObject(
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val price: Int = 0,
        val imageUrl: String = "",
    )
        @Serializable
    object LoginScreenObject: NavRoutes()

       @Serializable
    object MapScreenObject: NavRoutes()


    @Serializable
    data class AddScreenObject(
        val title: String = "",
        val description: String = "",
        val price: String = "",
        val category: String = "Select category",
        val key: String = "",
        val imageUrl: String = "",
        val authorUid: String = "",
        val author: String = "",
        val timestamp: Long = System.currentTimeMillis(),
        val isFaves: Boolean  = false,

    ): NavRoutes()
}