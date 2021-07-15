package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainModel
import kotlinx.coroutines.flow.Flow

interface MainRepositorySource {
    val responseData: MutableLiveData<ResponseData<MainModel>>
    suspend fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean)
}