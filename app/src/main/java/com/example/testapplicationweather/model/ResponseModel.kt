package com.example.testapplicationweather.model

import com.example.testapplicationweather.main.model.MainModel

data class ResponseModel(
    val success: MainModel = MainModel(),
    val failure: String = "",
)
