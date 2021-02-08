package com.kamo.globalkineticweatherapp.data

import com.kamo.globalkineticweatherapp.data.local.LocalDataSource
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource,
                                         private val remoteDataSource: RemoteDataSource,
                                         private val coroutineDispatcher: CoroutineDispatcher
): Repository {

    override suspend fun getWeatherForecastByLatAndLong(apiKey: String,
                                                        lat: Double,
                                                        long: Double):Result<WeatherForecast>{
        return withContext(coroutineDispatcher) {

            val remoteResults = remoteDataSource.getWeatherForecastByLatAndLong(apiKey, lat, long)
            when(remoteResults){
                is Result.Success->{
                    localDataSource.saveWeatherForecast(remoteResults.data)
                    return@withContext Result.Success(remoteResults.data)
                }
                is Result.Error -> {
                    return@withContext Result.Error(remoteResults.message)
                }
            }
            return@withContext remoteResults
        }
    }


    override suspend fun getLocalWeatherForecast():Result<WeatherForecast>{
        return localDataSource.getWeatherForecast()
    }

}