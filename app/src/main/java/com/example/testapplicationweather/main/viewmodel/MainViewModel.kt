package com.example.testapplicationweather.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.main.model.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val weather = repository.listOfWeather
    val error = repository.error
    var internet = repository.internet
    fun initRetrofit(coordinates: String) = repository.initRetrofit(coordinates)

}