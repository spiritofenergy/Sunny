package com.kodex.sunny.addScreen.data

import com.kodex.sunny.utils.Categories
import kotlinx.serialization.Serializable

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
    )

