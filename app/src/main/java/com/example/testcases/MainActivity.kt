package com.example.testcases

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testcases.ui.theme.TestCasesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    class MainViewModelFactory(val newsList: MutableState<List<NewsCategory>>) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(newsList = newsList) as T
        }
    }
    lateinit var viewmodel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var articles: MutableState<List<NewsCategory>> = mutableStateOf(listOf())
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.Default) {
            try {
                val list = mutableListOf<NewsCategory>()/*
                val resp = newsApiService.getTopHeadlines(apiKey,NewsCategoryEnum.BUSINESS.categoryName)
                Log.d("TAG",resp.articles.toString())*/
                NewsCategoryEnum.values().forEach { category ->
                    val response = RetrofitClient.newsApiService.getTopHeadlines(RetrofitClient.API_KEY, category.categoryName)
                    val newsCat = NewsCategory(category.categoryLabel,response.articles)
                    list.add(newsCat)
                    Log.d("TAG",list.toString())
                }
                articles.value = list.sortedBy { it.name } // sort

            } catch (e: Exception) {
                Log.d("TAG111",e.message.toString())
                // Handle exception or error during the API request
            }
        }
        val viewmodel =ViewModelProvider(this,MainViewModelFactory(articles))[MainViewModel::class.java]
        setContent {
            TestCasesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    
                    MainScreen(viewmodel = viewmodel)
                }
            }
        }
    }

    suspend fun loadNewsByCategory(category: NewsCategoryEnum): NewsCategory {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        var articles = listOf<Article>()
        try {
            val response = newsApiService.getTopHeadlines("6ef6b129268941798463d647c73e91f6", category.categoryName)

            if (response.status == "ok") {
                articles = response.articles
                Log.d("TAG",articles.toString())
            } else {
                response.status?.let { Log.d("TAG", it) }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NewsCategory(category.categoryName, articles)
    }

    suspend fun loadNewsAllCategories(): List<NewsCategory>{

        val list = mutableListOf<NewsCategory>()
        NewsCategoryEnum.values().forEach { category ->
            CoroutineScope(Dispatchers.Main).launch {
                list.add(loadNewsByCategory(category))
                Log.d("TAG",list.toString())
            }
        }
        return list
    }
}
