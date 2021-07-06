package com.example.testapplicationweather.main.model

import com.google.gson.annotations.SerializedName

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
    val data: List<DayModel> = listOf(DayModel("", "", ""))
)

data class DayModel(
    val temperatureHigh: String,
    val icon: String,
    val time: String,
)