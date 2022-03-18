package com.example.newsfeed.api

import com.example.newsfeed.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiNewsFeed by lazy {
        retrofit.create(ApiNewsFeed::class.java)
    }

}