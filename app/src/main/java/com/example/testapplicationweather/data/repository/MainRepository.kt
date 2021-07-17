package com.example.testapplicationweather.data.repository

import com.example.testapplicationweather.data.DataSource
import com.example.testapplicationweather.data.model.WeatherModel
import com.example.testapplicationweather.mapper.WeatherMapper
import com.example.testapplicationweather.utilites.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MainRepository(
    private val dataSource: DataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MainRepositorySource {
    override suspend fun getWeatherFromRetrofit(
        coordinates: String,
        needCache: Boolean
    ): Result<WeatherModel> =
        withContext(ioDispatcher) {
            val mapper = WeatherMapper()
            when (val response = dataSource.getResult(coordinates, needCache)) {
                is Result.Success -> {
                    if (response.data != null) {
                        Result.Success(mapper.transformToDomain(response.data))
                    } else {
                        Result.Success(null)
                    }
                }
                is Result.Failure -> {
                    Result.Failure(response.exception)
                }
                else -> {
                    Result.Loading
                }
            }

        }
}