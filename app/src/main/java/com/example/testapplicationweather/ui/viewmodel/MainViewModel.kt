package com.example.testapplicationweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplicationweather.data.model.WeatherModel
import com.example.testapplicationweather.data.repository.MainRepository
import com.example.testapplicationweather.utilites.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    val weather = MutableLiveData<WeatherModel?>()

    fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean = false) {
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getWeatherFromRetrofit(coordinates, needCache)) {
                is Result.Success -> {
                    isLoading.value = false
                    if (result.data != null) {
                        weather.value = result.data
                    } else {
                        weather.postValue(null)
                    }
                }
                is Result.Failure -> {
                    isLoading.value = false
                }
                is Result.Loading -> {
                    isLoading.postValue(true)
                }
            }

        }
    }

}