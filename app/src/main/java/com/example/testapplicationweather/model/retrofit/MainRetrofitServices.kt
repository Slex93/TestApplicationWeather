package com.example.testapplicationweather.model.retrofit

import com.example.testapplicationweather.main.model.MainModel
import com.example.testapplicationweather.utilites.API_GET
import com.example.testapplicationweather.utilites.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRetrofitServices {

    @GET("/$API_GET/$API_KEY/{coordinates}")
    fun getWeather(
        @Path("coordinates") coordinates: String
    ): Call<MainModel>

}