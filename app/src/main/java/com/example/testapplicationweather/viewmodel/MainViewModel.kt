package com.example.testapplicationweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplicationweather.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val responseData = repository.responseData

    fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean = false) =
        viewModelScope.launch {
            repository.getWeatherFromRetrofit(coordinates, needCache)
        }

}