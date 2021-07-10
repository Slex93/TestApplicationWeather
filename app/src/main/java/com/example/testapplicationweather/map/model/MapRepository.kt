package com.example.testapplicationweather.map.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainModel
import com.example.testapplicationweather.map.model.retrofit.MapRetrofitClient
import com.example.testapplicationweather.map.model.retrofit.MapRetrofitService
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapRepository {

    private val retrofitService: MapRetrofitService
    get() = MapRetrofitClient.getClient(BASE_URL)
        .create(MapRetrofitService::class.java)

    val currentWeather = MutableLiveData<MapModel>()
    val error = MutableLiveData<String>()

    fun initRetrofit(coordinates: String) {
        val mService = retrofitService
        mService.getMovie(coordinates)
            .enqueue(object : Callback<MapModel> {
                override fun onResponse(
                    call: Call<MapModel>,
                    response: Response<MapModel>
                ) {
                    if (response.isSuccessful) {
                        currentWeather.value = response.body()?.copy() as MapModel
                    } else {
                        error.value = response.message()
                    }
                }

                override fun onFailure(call: Call<MapModel>, t: Throwable) {
                    error.value = t.message
                }
            })
    }
}