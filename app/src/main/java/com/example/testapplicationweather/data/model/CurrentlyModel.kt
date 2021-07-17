package com.example.testapplicationweather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentlyModel(
    @SerializedName("temperature")
    val temperature: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("icon")
    val icon: String = "",
)
