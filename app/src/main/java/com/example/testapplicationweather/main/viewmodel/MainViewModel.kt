package com.example.testapplicationweather.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.main.model.DailyModel
import com.example.testapplicationweather.main.model.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val listOfDays = MutableLiveData<DailyModel>()

    val weather = repository.listOfWeather
    val error = repository.error

    fun initRetrofit(coordinates: String) {
        repository.initRetrofit(coordinates)
    }

}