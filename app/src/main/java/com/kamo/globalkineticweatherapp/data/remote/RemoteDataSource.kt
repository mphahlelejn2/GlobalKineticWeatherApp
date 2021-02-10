package com.kamo.globalkineticweatherapp.data.remote

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates

interface RemoteDataSource{
    suspend fun getWeatherForecastByLatAndLong(
        apiKey: String,
        gpsCoordinates: GpsCoordinates
    ): Result<WeatherForecast>
}