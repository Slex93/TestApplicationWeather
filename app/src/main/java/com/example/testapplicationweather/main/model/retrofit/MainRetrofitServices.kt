package com.example.testapplicationweather.main.model.retrofit

import com.example.testapplicationweather.main.model.MainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRetrofitServices {

    @GET("/forecast/3e7e519ea86c8e3fcf67c0f4870513d7/{coordinates}")
    fun getMovie(
        @Path("coordinates") coordinates: String
    ): Call<MainModel>

}