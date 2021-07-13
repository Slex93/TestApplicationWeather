package com.example.testapplicationweather.model

import com.example.testapplicationweather.map.model.MapModel
import com.example.testapplicationweather.utilites.API_GET
import com.example.testapplicationweather.utilites.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MapRetrofitService {

    @GET("/$API_GET/$API_KEY/{coordinates}")
    fun getMovie(
        @Path("coordinates") coordinates: String
    ): Call<MapModel>

}