package com.example.newsfeed.repository

import com.example.newsfeed.api.RetrofitInstance
import com.example.newsfeed.models.MainModelNewsFeed
import retrofit2.Response

class Repository {

    suspend fun getPost(country: String): Response<MainModelNewsFeed> {
        return RetrofitInstance.api.getNewsPost(country)
    }

    suspend fun getPostByCategory(country: String, category: String): Response<MainModelNewsFeed> {
        return RetrofitInstance.api.getNewsByCategoryPost(country, category)
    }

    suspend fun searchNewsByPopularity(search: String, language: String): Response<MainModelNewsFeed> {
        return RetrofitInstance.api.searchNewsSortByPopularity(search, language)
    }

}