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

    private var needCache = false

    private val retrofitService: MainRetrofitServices
        get() = MainRetrofitClient.getClient(BASE_URL, needCache)
            .create(MainRetrofitServices::class.java)

    override val listOfWeather = MutableLiveData<MainModel>()

    override val error = MutableLiveData<String>()

    override fun initRetrofitService(coordinates: String, needCache: Boolean) {
        this.needCache = needCache
        val mService = retrofitService
        mService.getWeather(coordinates)
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