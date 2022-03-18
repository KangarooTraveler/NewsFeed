package com.example.newsfeed.api

import com.example.newsfeed.models.MainModelNewsFeed
import com.example.newsfeed.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNewsFeed {

    @GET("/v2/top-headlines?apiKey=${Constants.API_KEY}")
    suspend fun getNewsPost(
        @Query("country") country: String
    ): Response<MainModelNewsFeed>

    @GET("/v2/top-headlines?apiKey=${Constants.API_KEY}")
    suspend fun getNewsByCategoryPost(
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<MainModelNewsFeed>

    @GET("v2/everything?sortBy=popularity&apiKey=${Constants.API_KEY}")
    suspend fun searchNewsSortByPopularity(
        @Query("q") search: String,
        @Query("language") language: String
    ): Response<MainModelNewsFeed>

}