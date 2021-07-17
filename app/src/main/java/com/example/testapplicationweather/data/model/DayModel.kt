package com.example.testapplicationweather.data.model

import com.google.gson.annotations.SerializedName

data class DayModel(
    @SerializedName("temperatureHigh")
    val temperatureHigh: String,
    @SerializedName("temperatureLow")
    val temperatureLow: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("time")
    val time: String,
)