package com.example.testcases

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(): ViewModel() {
    val newsList: MutableState<List<NewsCategory>> = mutableStateOf(listOf())
/*    fun onSearchChange(text: String){
        _searchText.value= text
        val filteredData = if (_searchText.value.isBlank()) {
            // If the search text is empty, show all data
            currentCurrencyList.toList()
        } else {
            currentCurrencyList.filter { currency ->
                currency.name.contains(_searchText.value, ignoreCase = true)
            }
        }
        _data.value = filteredData
    }*/
    init {
    CoroutineScope(Dispatchers.Main).launch(Dispatchers.Default) {
        try {
            val list = mutableListOf<NewsCategory>()
            NewsCategoryEnum.values().forEach { category ->
                val response = RetrofitClient.newsApiService.getTopHeadlines(RetrofitClient.API_KEY, category.categoryName)
                val newsCat = NewsCategory(category.categoryLabel,response.articles)
                list.add(newsCat)
                Log.d("TAG",list.toString())
            }
            newsList.value = list.sortedBy { it.name } // sort

        } catch (e: Exception) {
            Log.d("TAG111",e.message.toString())
            // Handle exception or error during the API request
        }
    }
    }
}