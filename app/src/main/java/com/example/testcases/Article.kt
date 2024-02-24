package com.example.testcases

import androidx.compose.ui.graphics.ImageBitmap



data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: String,
    val content: String?,
    val imageBitmap: ImageBitmap?,
    val categoryLabel: String
)