package com.example.testcases.model

import com.example.testcases.model.Article

data class NewsCategory(
    val name: String,
    val articleList: List<Article>
)

