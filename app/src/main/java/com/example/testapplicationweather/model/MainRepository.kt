package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainBase
import com.example.testapplicationweather.model.retrofit.MainRetrofitClient
import com.example.testapplicationweather.model.retrofit.MainRetrofitServices
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository : MainRepositorySource {

    override val responseData = MutableLiveData<ResponseBase<MainBase>>()

    override suspend fun getWeatherFromRetrofit(
        coordinates: String,
        needCache: Boolean
    ) {
        initServiceRetrofit(coordinates, needCache)
    }

    private fun initServiceRetrofit(location: String, needCache: Boolean) {

        val client: MainRetrofitServices = MainRetrofitClient().getClient(BASE_URL, needCache)
            .create(MainRetrofitServices::class.java)

        client.getWeather(location).enqueue(object : Callback<MainBase> {
            override fun onResponse(call: Call<MainBase>, response: Response<MainBase>) {
                responseData.value = ResponseBase(success = response.body()?.copy())
            }

            override fun onFailure(call: Call<MainBase>, t: Throwable) {
                responseData.value = ResponseBase(failure = t.toString())
            }

        })
    }
}