package com.kamo.globalkineticweatherapp.data.remote

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates
import javax.inject.Inject

class RemoteRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService): BaseRemoteDataSource(),
    RemoteDataSource
{
    override suspend fun getWeatherForecastByLatAndLong(
        apiKey: String,
        gpsCoordinates: GpsCoordinates): Result<WeatherForecast> {

        return getResult { apiService.getWeatherForecastByLatAndLong(apiKey,gpsCoordinates.latitude.toString(),gpsCoordinates.longitude.toString())}
    }
}