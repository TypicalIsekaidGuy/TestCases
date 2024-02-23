package com.example.testcases

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val BASE_URL = "https://newsapi.org"
    val API_KEY = "6ef6b129268941798463d647c73e91f6"
    val NEWS_COUNT = 20

    val newsApiService: NewsApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(NewsApiService::class.java)
    }


}