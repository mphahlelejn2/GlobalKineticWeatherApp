package com.kamo.globalkineticweatherapp.weatherForecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kamo.globalkineticweatherapp.data.Event
import com.kamo.globalkineticweatherapp.data.Repository
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WeatherForecastViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel(){

    private val _allWeatherForecasts = MutableLiveData<Result<WeatherForecast>>()
    val allWeatherForecasts: LiveData<Result<WeatherForecast>> = _allWeatherForecasts

    private val _refreshEvent = MutableLiveData<Event<Unit>>()
    val refreshEvent: LiveData<Event<Unit>> = _refreshEvent

    fun getWeatherForecasts() {
            repository.getOffLineWeatherForecast()
            .onEach {
                _allWeatherForecasts.value = it }
            .launchIn(viewModelScope)

    }

    fun refresh(apiKey: String, gpsCoordinates: GpsCoordinates?) {
        if(gpsCoordinates!=null)
            repository.refreshWeatherForecast(apiKey, gpsCoordinates).onEach {
                _allWeatherForecasts.value = it
            }
                .launchIn(viewModelScope)
    }

    fun refresh()
    {
        _refreshEvent.value=Event(Unit)
    }

}