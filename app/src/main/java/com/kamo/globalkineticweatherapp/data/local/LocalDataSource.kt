package com.kamo.globalkineticweatherapp.data.local

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getWeatherForecast(): Result<WeatherForecast>
    suspend fun saveWeatherForecast(data: WeatherForecast)
}