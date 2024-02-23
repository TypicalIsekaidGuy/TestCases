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

class MainViewModel(val newsRepo: NewsRepository): ViewModel() {
    val TAG = "MAIN_VIEWMODEL"
    val newsList: MutableState<List<NewsCategory>>  get() = newsRepo.newsList
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
    viewModelScope.launch {
        newsRepo.fetchNewsCategories()
    }
    }
}