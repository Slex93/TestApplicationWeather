package com.example.testapplicationweather.mapper

import com.example.testapplicationweather.data.model.WeatherModel
import com.example.testapplicationweather.data.model.WeatherRemoteModel

class WeatherMapper : BaseMapper<WeatherRemoteModel, WeatherModel> {
    override fun transformToDomain(type: WeatherRemoteModel): WeatherModel = WeatherModel(
        currently = type.currently,
        daily = type.daily
    )

    override fun transformToDto(type: WeatherModel): WeatherRemoteModel = WeatherRemoteModel(
        currently = type.currently,
        daily = type.daily
    )
}