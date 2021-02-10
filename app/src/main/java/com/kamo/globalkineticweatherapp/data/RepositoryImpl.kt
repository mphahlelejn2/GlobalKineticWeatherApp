package com.kamo.globalkineticweatherapp.data

import com.kamo.globalkineticweatherapp.common.AppConstants.ERROR
import com.kamo.globalkineticweatherapp.common.AppConstants.NETWORK_ERROR
import com.kamo.globalkineticweatherapp.common.AppConstants.NO_DATA
import com.kamo.globalkineticweatherapp.data.local.LocalDataSource
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.data.remote.RemoteDataSource
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource,
                                         private val remoteDataSource: RemoteDataSource
): Repository {

    override fun getOffLineWeatherForecast(): Flow<Result<WeatherForecast>> = localDataSource.getWeatherForecast()


    override fun refreshWeatherForecast(apiKey: String, gpsCoordinates: GpsCoordinates): Flow<Result<WeatherForecast>> = getRemoteDataOperation(
        network = { remoteDataSource.getWeatherForecastByLatAndLong(apiKey,gpsCoordinates) },
        save = { localDataSource.saveWeatherForecast( it) }
    )

    private fun  getRemoteDataOperation(network: suspend () -> Result<WeatherForecast>,
                              save: suspend (WeatherForecast) -> Unit) = flow{

            emit(Result.Loading)
            val responseStatus = network.invoke()
            if (responseStatus is  Result.Success) {
                emit(Result.Success(responseStatus.data))
                save(responseStatus.data)
            } else if (responseStatus is Result.Error) {
                emit(Result.Error(NETWORK_ERROR))
            }

        }.flowOn(Dispatchers.IO)


}