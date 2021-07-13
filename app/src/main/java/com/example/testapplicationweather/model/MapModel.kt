package com.example.testapplicationweather.map.model

data class MapModel(
    val currently: MapCurrentlyModel,
)

data class MapCurrentlyModel(
    val temperature: String,
    val summary: String,
    val icon: String,
)