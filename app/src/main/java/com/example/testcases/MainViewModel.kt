package com.example.testcases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcases.data.NewsRepository
import kotlinx.coroutines.launch

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