package com.example.testapplicationweather.model

import com.example.testapplicationweather.main.model.MainModel

data class ResponseData<T>(
    val success: T? = null,
    val failure: String? = null,
)
