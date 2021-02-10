package com.kamo.globalkineticweatherapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kamo.globalkineticweatherapp.data.Repository
import com.kamo.globalkineticweatherapp.gps.GpsViewModel
import com.kamo.globalkineticweatherapp.gps.IGPSDeviceManager
import com.kamo.globalkineticweatherapp.gps.IGPSLocation
import com.kamo.globalkineticweatherapp.weatherForecast.WeatherForecastViewModel

class ViewModelFactory() : ViewModelProvider.Factory {
    
    private lateinit var gpsDeviceManager: IGPSDeviceManager
    private lateinit var locationFinder: IGPSLocation
    private lateinit var fusedLocation: IGPSLocation
    private lateinit var repository: Repository

    constructor(gpsDeviceManager: IGPSDeviceManager,fusedLocation: IGPSLocation, locationFinder: IGPSLocation):this() {
        this.gpsDeviceManager=gpsDeviceManager
        this.fusedLocation=fusedLocation
        this.locationFinder=locationFinder
    }

    constructor(repository: Repository):this() {
        this.repository=repository
    }

    companion object {
        fun getInstance(gpsDeviceManager: IGPSDeviceManager,fusedLocation: IGPSLocation, locationFinder: IGPSLocation) = ViewModelFactory(gpsDeviceManager, locationFinder, fusedLocation)
        fun getInstance(repository: Repository)= ViewModelFactory(repository)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        with(modelClass) {
            return when {
                isAssignableFrom(GpsViewModel::class.java) ->  GpsViewModel(gpsDeviceManager, locationFinder, fusedLocation)
                isAssignableFrom(WeatherForecastViewModel::class.java) ->  WeatherForecastViewModel(repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }

}