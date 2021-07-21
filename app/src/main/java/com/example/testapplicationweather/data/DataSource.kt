package com.example.testapplicationweather.data

import com.example.testapplicationweather.data.model.WeatherRemoteModel
import com.example.testapplicationweather.data.retrofit.MainRetrofitClient
import com.example.testapplicationweather.data.retrofit.MainRetrofitServices
import com.example.testapplicationweather.utilites.BASE_URL
import com.example.testapplicationweather.utilites.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource {

    suspend fun getResult(coordinates: String, needCache: Boolean): Result<WeatherRemoteModel> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val client: MainRetrofitServices =
                    MainRetrofitClient().getClient(BASE_URL, needCache)
                        .create(MainRetrofitServices::class.java)
                val result = client.getWeather(coordinates)
                if (result.isSuccessful) {
                    val data = result.body()
                    Result.Success(data)
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Failure(exception.toString())
            }
        }
}