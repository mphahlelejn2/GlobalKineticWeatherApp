package com.kamo.globalkineticweatherapp.data

import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getWeatherForecastByLatAndLong(
        apiKey: String,
        lat: Double,
        long: Double
    ): Result<WeatherForecast>

    suspend fun getLocalWeatherForecast(): Result<WeatherForecast>
}