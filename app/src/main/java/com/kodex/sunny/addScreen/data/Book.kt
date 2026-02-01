package com.kodex.sunny.addScreen.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kodex.sunny.utils.Categories

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val key: String = "",
    val title: String = "",
    val searchTitle: String = title.lowercase(),
    val description: String = "",
    val price: Int = 0,
    val categoryIndex: Int = Categories.ALL,
    val imageUrl: String = "",
    val author: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isFaves: Boolean = false,
    val ratingsList: List<Int> = emptyList()
)
