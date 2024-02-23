package com.example.testcases

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = RetrofitClient.NEWS_COUNT
    ): NewsResponse

}
