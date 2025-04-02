package com.example.firebase

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("/v2/top-headlines")
    fun getNews(
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "ce69a1eae3b2435390ed4f5a3ded74be",
        @Query("pageSize") pageSize: Int = 30
    ): Call<News>
}
