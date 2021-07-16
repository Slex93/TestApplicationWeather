package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainModel
import com.example.testapplicationweather.model.retrofit.MainRetrofitClient
import com.example.testapplicationweather.model.retrofit.MainRetrofitServices
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository : MainRepositorySource {

    override val responseData = MutableLiveData<ResponseModel>()

    override suspend fun getWeatherFromRetrofit(
        coordinates: String,
        needCache: Boolean
    ) {
        initServiceRetrofit(coordinates, needCache)
    }

    private fun initServiceRetrofit(location: String, needCache: Boolean) {

        val client: MainRetrofitServices = MainRetrofitClient().getClient(BASE_URL, needCache)
            .create(MainRetrofitServices::class.java)

        client.getWeather(location).enqueue(object : Callback<MainModel> {
            override fun onResponse(call: Call<MainModel>, response: Response<MainModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        responseData.value = ResponseModel(success = it.copy())
                    }
                }
            }

            override fun onFailure(call: Call<MainModel>, t: Throwable) {
                responseData.value = ResponseModel(failure = t.toString())
            }

        })
    }
}