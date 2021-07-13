package com.example.testapplicationweather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.model.MapRepository

class MapViewModel(private val repository: MapRepository) : ViewModel() {

    val currentWeather = repository.currentWeather
    val error = repository.error
    fun initRetrofit(coordinates: String) = repository.initRetrofit(coordinates)
}