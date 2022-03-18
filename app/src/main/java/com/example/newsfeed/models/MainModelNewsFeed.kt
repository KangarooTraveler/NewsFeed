package com.example.newsfeed.models

import com.google.gson.annotations.SerializedName

data class MainModelNewsFeed(
    @SerializedName("articles")
    val articles: List<Articles>
)

data class Articles(
    @SerializedName("source")
    val source: Source,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String,
    @SerializedName("publishedAt")
    val publishedAt: String
    )

data class Source(
    @SerializedName("name")
    val name: String
)