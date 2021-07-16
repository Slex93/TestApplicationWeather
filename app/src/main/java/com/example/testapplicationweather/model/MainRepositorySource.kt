package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainBase

interface MainRepositorySource {
    val responseData: MutableLiveData<ResponseBase<MainBase>>
    suspend fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean)
}