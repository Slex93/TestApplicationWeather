package com.example.testapplicationweather.data.retrofit

import com.example.testapplicationweather.data.model.WeatherRemoteModel
import com.example.testapplicationweather.utilites.API_GET
import com.example.testapplicationweather.utilites.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRetrofitServices {

    @GET("/$API_GET/$API_KEY/{coordinates}")
    suspend fun getWeather(
        @Path("coordinates") coordinates: String
    ): Response<WeatherRemoteModel>

}