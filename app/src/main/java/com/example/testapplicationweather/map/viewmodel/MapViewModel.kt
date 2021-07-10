package com.example.testapplicationweather.map.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.map.model.MapRepository

class MapViewModel(private val repository: MapRepository) : ViewModel() {

    val currentWeather = repository.currentWeather
    val error = repository.error
    fun initRetrofit(coordinates: String) = repository.initRetrofit(coordinates)
}