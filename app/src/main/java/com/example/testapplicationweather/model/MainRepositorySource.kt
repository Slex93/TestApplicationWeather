package com.example.testapplicationweather.model

import androidx.lifecycle.MutableLiveData
import com.example.testapplicationweather.main.model.MainModel

interface MainRepositorySource {
    val listOfWeather: MutableLiveData<MainModel>
    val error: MutableLiveData<String>
    fun initRetrofitService(coordinates: String, needCache: Boolean)
}