package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainModel
import com.example.testapplicationweather.utilites.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    private val retrofitService: MainRetrofitServices
        get() = MainRetrofitClient.getClient(BASE_URL)
            .create(MainRetrofitServices::class.java)

    val listOfWeather = MutableLiveData<MainModel>()

    val error = MutableLiveData<String>()

    fun initRetrofit(coordinates: String) {
        val mService = retrofitService
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