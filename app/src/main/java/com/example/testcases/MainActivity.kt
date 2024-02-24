package com.example.testcases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testcases.data.Dependencies
import com.example.testcases.data.NewsRepository
import com.example.testcases.ui.theme.TestCasesTheme

class MainActivity : ComponentActivity() {
    class MainViewModelFactory(val newsRepository: NewsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(newsRepository) as T
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel =ViewModelProvider(this,MainViewModelFactory(Dependencies.newsRepository))[MainViewModel::class.java]
        setContent {
            TestCasesTheme {
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

}
