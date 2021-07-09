package com.example.testapplicationweather.main.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.retrofit.MainRetrofitClient
import com.example.testapplicationweather.main.model.retrofit.MainRetrofitServices
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainRepository {

    private val firstRetrofitService: MainRetrofitServices
        get() = MainRetrofitClient.getClient(BASE_URL)
            .create(MainRetrofitServices::class.java)

    val listOfWeather = MutableLiveData<MainModel>()

    val error = MutableLiveData<String>()

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