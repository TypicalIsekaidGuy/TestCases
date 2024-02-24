package com.example.testcases

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcases.utils.toBitmap
import com.example.testcases.utils.toByteArray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MainViewModel(
    private val newsRepo: NewsRepository,
    private val newsDao: NewsDao,
    private val hasInternetConnection: Boolean
): ViewModel() {
    val TAG = "MAIN_VIEWMODEL"

    private val _statusMessage = MutableStateFlow<String?>(null)
    val message: StateFlow<String?>
        get() = _statusMessage
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
            newsRepo.fetchNewsCategories(
                { categoriesList -> cacheToDB(categoriesList) },
                ::fetchFromDBNewsCategories,
                hasInternetConnection
            )
        }
    }

    private fun fetchFromDBNewsCategories(): List<NewsCategory> {
        var articleList: List<ArticleEntity> = emptyList()
        try {
            articleList = newsDao.getArticles()
        } catch (e: Exception) {
            e.printStackTrace()
            _statusMessage.value = "Try checking Internet"
        }

        return articleList.groupBy { it.categoryLabel }
            .map { (categoryName, articles) ->
                NewsCategory(categoryName, articles.map { article ->

                    Article(
                        article.source,
                        article.author,
                        article.title,
                        article.description,
                        article.url,
                        article.publishedAt,
                        article.content,
                        if(article.imageByteArray==null) null else article.imageByteArray.toBitmap(),
                        article.categoryLabel
                    )
                })
            }
    }

    private suspend fun cacheToDB(categoriesList: List<NewsCategory>) {
        val articleEntities = categoriesList.flatMap { it.articleList.map { article ->
            ArticleEntity(
                0,
                article.source,
                article.author,
                article.title,
                article.description,
                article.url,
                article.publishedAt,
                article.content,
                if(article.imageBitmap==null) null else article.imageBitmap.asAndroidBitmap().toByteArray(),
                article.categoryLabel
            )
        } }
        try {
            newsDao.insertArticles(articleEntities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}