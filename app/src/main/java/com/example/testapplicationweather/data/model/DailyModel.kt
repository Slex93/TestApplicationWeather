package com.example.testapplicationweather.data.model

import com.google.gson.annotations.SerializedName

data class DailyModel(
    @SerializedName("data")
    val data: List<DayModel> = emptyList()
)