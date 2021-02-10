package com.kamo.globalkineticweatherapp.data

import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun refreshWeatherForecast(
        apiKey: String,
        gpsCoordinates: GpsCoordinates
    ): Flow<Result<WeatherForecast>>

    fun getOffLineWeatherForecast(): Flow<Result<WeatherForecast>>
}