package com.example.testapplicationweather.data.repository

import com.example.testapplicationweather.data.model.WeatherModel
import com.example.testapplicationweather.utilites.Result

interface MainRepositorySource {
    suspend fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean): Result<WeatherModel>
}