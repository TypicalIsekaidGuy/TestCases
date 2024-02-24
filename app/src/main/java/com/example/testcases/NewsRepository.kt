package com.example.testcases

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

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
/*                var imageByteArray: ByteArray?  = null*/
                var imageBitmap: Bitmap?  = null
                val articleEntities = response.articles.map { article ->
                    try {
                        if(!article.urlToImage.isNullOrEmpty()){
                            imageBitmap = withContext(Dispatchers.IO) {


                                Picasso.get().load(article.urlToImage).get()


                            }
                            /*                        val bitmap = imageBitmap
                                                    val stream = ByteArrayOutputStream()
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                                                    imageByteArray = stream.toByteArray()*/
                        }

                    }
                    catch (e: Exception){
                        e.printStackTrace()
                    }

                    ArticleEntity(
                        source = article.source,
                        author = article.author,
                        title = article.title,
                        description = article.description,
                        url = article.url,
                        publishedAt = article.publishedAt,
                        content = article.content,
                        imageBitmap = imageBitmap?.asImageBitmap(),
                        categoryName = category.categoryName
                    )
                }
                val newsCat = NewsCategory(category.categoryLabel, articleEntities)
                newsCategories.add(newsCat)
                Log.d(TAG, newsCategories.toString())
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }

        newsList.value = newsCategories.sortedBy { it.name }
    }
}
