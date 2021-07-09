package com.example.testapplicationweather.main.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.retrofit.MainRetrofitClient
import com.example.testapplicationweather.main.model.retrofit.MainRetrofitServices
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    private val firstRetrofitService: MainRetrofitServices
        get() = MainRetrofitClient.getClient(BASE_URL, internet)
            .create(MainRetrofitServices::class.java)

    val listOfWeather = MutableLiveData<MainModel>()

    val error = MutableLiveData<String>()

    val internet: Boolean = false

    fun initRetrofit(coordinates: String) {
        val mService = firstRetrofitService
        mService.getMovie(coordinates)
            .enqueue(object : Callback<MainModel> {
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    if (response.isSuccessful) {
                        listOfWeather.value = response.body()?.copy() as MainModel

                    } else {
                        error.value = response.message()
                    }

                }

                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    error.value = t.message
                }
            })
    }
}