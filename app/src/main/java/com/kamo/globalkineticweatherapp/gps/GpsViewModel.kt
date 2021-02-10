package com.kamo.globalkineticweatherapp.gps

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamo.globalkineticweatherapp.data.Event

class GpsViewModel @ViewModelInject constructor(private var gpsDeviceManager: IGPSDeviceManager,
                                               private var fusedLocation: IGPSLocation,
                                               private var locationFinder: IGPSLocation
                   ): ViewModel(){

    private val _locationFinderGPSCoordinates=MutableLiveData<GpsCoordinates>()
    val locationFinderGPSCoordinates:LiveData<GpsCoordinates> =_locationFinderGPSCoordinates


    private val _requestGPSPermissionsEvent=MutableLiveData<Event<Unit>>()
    val requestGPSPermissionsEvent:LiveData<Event<Unit>> =_requestGPSPermissionsEvent

    fun requestGPSPermissionsEvent() {
        _requestGPSPermissionsEvent.value = Event(Unit)
    }

    fun requestGPSCoordinates() {

        val fusedLocationCoordinatesFun={gpsCoordinates: GpsCoordinates ->
            _locationFinderGPSCoordinates.value=gpsCoordinates
        }

        val locationFinderCoordinatesFun={gpsCoordinates: GpsCoordinates ->
            _locationFinderGPSCoordinates.value=gpsCoordinates
        }

        fusedLocation.registerFunctionToGetResults(fusedLocationCoordinatesFun)
        fusedLocation.getGPSLocation()
    }

     fun turnOnGPS() {
        if ((!gpsDeviceManager.isGPSOn()))
            gpsDeviceManager.turnOnGPS()
    }

     fun isGPSDeviceOn() =gpsDeviceManager.isGPSOn()

}