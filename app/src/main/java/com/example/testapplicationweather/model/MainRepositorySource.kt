package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData

interface MainRepositorySource {
    val responseData: MutableLiveData<ResponseModel>
    suspend fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean)
}