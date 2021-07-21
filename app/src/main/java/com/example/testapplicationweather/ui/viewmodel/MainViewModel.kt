package com.example.testapplicationweather.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplicationweather.data.model.WeatherModel
import com.example.testapplicationweather.data.repository.MainRepository
import com.example.testapplicationweather.utilites.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _weather = MutableLiveData<WeatherModel?>()
    private fun setWeather(weather: WeatherModel?) {
        _weather.value = weather
    }

    val weather: LiveData<WeatherModel?> get() = _weather

    fun getWeatherFromRetrofit(coordinates: String, needCache: Boolean = false) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getWeatherFromRetrofit(coordinates, needCache)) {
                is Result.Success -> {
                    setLoading(false)
                    if (result.data != null) {
                        setWeather(result.data)
                    } else {
                        setWeather(null)
                    }
                }
                is Result.Failure -> {
                    setLoading(false)
                }
                is Result.Loading -> {
                    setLoading(true)
                }
            }

        }
    }

}