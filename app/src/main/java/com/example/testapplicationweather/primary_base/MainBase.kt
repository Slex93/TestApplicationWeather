package com.example.testapplicationweather.main.model

import com.google.gson.annotations.SerializedName

data class MainBase(
    @SerializedName("currently") val currently: CurrentlyModel,
    @SerializedName("daily") val daily: DailyModel,
)

data class CurrentlyModel(
    @SerializedName("temperature") val temperature: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("icon") val icon: String,
)

data class DailyModel(
    @SerializedName("data") val data: List<DayModel>
)

data class DayModel(
    @SerializedName("temperatureHigh") val temperatureHigh: String,
    @SerializedName("temperatureLow") val temperatureLow: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("time") val time: String,
)