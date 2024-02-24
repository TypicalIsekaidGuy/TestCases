package com.example.testcases

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(val newsRepo: NewsRepository): ViewModel() {
    val TAG = "MAIN_VIEWMODEL"

/*    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    val newsList: MutableState<List<NewsCategory>>  get() = newsRepo.newsList
    fun onSearchChange(text: String){
        _searchText.value= text
        val filteredData = if (_searchText.value.isBlank()) {
            newsRepo.newsList.value
        } else {
            newsRepo.newsList.value.map {  }.filter { category ->
                category.name.contains(_searchText.value, ignoreCase = true)
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