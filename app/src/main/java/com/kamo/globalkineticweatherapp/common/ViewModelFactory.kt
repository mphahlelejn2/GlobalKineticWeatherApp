package com.kamo.globalkineticweatherapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kamo.globalkineticweatherapp.data.Repository
import com.kamo.globalkineticweatherapp.weatherForecast.WeatherForecastViewModel


class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    companion object {
        fun getInstance(repository: Repository)= ViewModelFactory(repository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java!!)) {
            return WeatherForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}