package com.example.testcases

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: String,
    val content: String?,
    val imageBitmap: ImageBitmap?,
    val categoryName: String
)