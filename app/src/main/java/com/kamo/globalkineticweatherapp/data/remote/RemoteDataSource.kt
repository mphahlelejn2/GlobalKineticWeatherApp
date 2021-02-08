package com.kamo.globalkineticweatherapp.data.remote

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast

interface RemoteDataSource{
    suspend fun getWeatherForecastByLatAndLong(
        apiKey: String?,
        lat: Double?,
        long: Double?
    ): Result<WeatherForecast>
}