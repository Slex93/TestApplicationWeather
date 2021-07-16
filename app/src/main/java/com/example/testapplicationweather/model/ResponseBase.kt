package com.example.testapplicationweather.model

data class ResponseBase<T>(
    val success: T? = null,
    val failure: String = "",
)
