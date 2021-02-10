package com.kamo.globalkineticweatherapp.data.local

import android.content.Context
import com.kamo.globalkineticweatherapp.common.AppConstants.DATA_NAME
import com.kamo.globalkineticweatherapp.common.SharedPrefWorker
import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor ( @ActivityContext private val context: Context):
    LocalDataSource {

    override fun getWeatherForecast(): Flow<Result<WeatherForecast>> {
        return SharedPrefWorker.getPrefData(context, DATA_NAME)
    }

    override suspend fun saveWeatherForecast(data: WeatherForecast) {
        SharedPrefWorker.setPrefString(context,DATA_NAME,data)
    }

}