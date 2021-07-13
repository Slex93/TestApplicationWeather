package com.example.testapplicationweather.model

interface MainRepositorySource {
    fun initRetrofitService(coordinates: String, needCache: Boolean = false)
}