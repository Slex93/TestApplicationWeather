package com.example.testapplicationweather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherRemoteModel(
    @SerializedName("currently") val currently: CurrentlyModel,
    @SerializedName("daily") val daily: DailyModel,
)