package com.example.testcases

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NewsRepository {
    val TAG = "NewsRepository"

    val newsList: MutableState<List<NewsCategory>> = mutableStateOf(listOf())


    suspend fun fetchNewsCategories(){
        val newsCategories = mutableListOf<NewsCategory>()

        try {
            NewsCategoryEnum.values().forEach { category ->
                val response = RetrofitClient.newsApiService.getTopHeadlines(
                    RetrofitClient.API_KEY,
                    category.categoryName
                )
                val newsCat = NewsCategory(category.categoryLabel, response.articles)
                newsCategories.add(newsCat)
                Log.d(TAG, newsCategories.toString())
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }

        newsList.value = newsCategories.sortedBy { it.name }
    }
}
