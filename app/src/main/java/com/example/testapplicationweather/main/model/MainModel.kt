package com.example.testapplicationweather.main.model

data class MainModel (
    val currently: CurrentlyModel,
    val daily: DailyModel,
)

data class CurrentlyModel(
    val temperature: String,
    val summary: String,
    val icon: String,
)

data class DailyModel(
    val data: List<DataModel>
)

data class DataModel(
    val day: DayModel
)
data class DayModel(
    val temperatureHigh: String,
    val icon: String,
)