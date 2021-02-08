package com.kamo.globalkineticweatherapp.data.local

import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor():
    LocalDataSource {

    override suspend fun getWeatherForecast(): Result<WeatherForecast> {
        TODO("Not yet implemented")
    }

    override suspend fun saveWeatherForecast(data: WeatherForecast) {
        TODO("Not yet implemented")
    }


}