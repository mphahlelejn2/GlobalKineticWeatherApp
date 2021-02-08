package com.kamo.globalkineticweatherapp.data.remote

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import javax.inject.Inject

class RemoteRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService): BaseRemoteDataSource(),
    RemoteDataSource
{
    override suspend fun getWeatherForecastByLatAndLong(apiKey: String?,
                                                lat: Double?,
                                                long: Double?): Result<WeatherForecast> {
        return getResult { apiService.getWeatherForecastByLatAndLong(apiKey,lat.toString(),long.toString())}
    }
}