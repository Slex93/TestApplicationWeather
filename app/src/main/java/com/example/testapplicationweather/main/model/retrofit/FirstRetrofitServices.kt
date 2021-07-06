package com.example.testapplicationweather.main.model.retrofit

import com.example.testapplicationweather.main.model.ModelHead
import retrofit2.Call
import retrofit2.http.GET

interface FirstRetrofitServices {

    @GET("/")
    fun getMovie(
    ): Call<ModelHead>

}