package com.example.newsfeed.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.models.MainModelNewsFeed
import com.example.newsfeed.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<Response<MainModelNewsFeed>> = MutableLiveData()
    var myResponseByCategory: MutableLiveData<Response<MainModelNewsFeed>> = MutableLiveData()
    var myResponseBySearch: MutableLiveData<Response<MainModelNewsFeed>> = MutableLiveData()

    fun getNewsPost(country: String){
        viewModelScope.launch {
            val response = repository.getPost(country)
            myResponse.value = response
        }
    }

    fun getNewsPostByCategory(country: String, category: String){
        viewModelScope.launch {
            val response = repository.getPostByCategory(country, category)
            myResponseByCategory.value = response
        }
    }

    fun searchNewsByPopularity(search: String, language: String){
        viewModelScope.launch {
            val response = repository.searchNewsByPopularity(search, language)
            myResponseBySearch.value = response
        }
    }

}