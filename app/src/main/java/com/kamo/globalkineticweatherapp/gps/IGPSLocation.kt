package com.kamo.globalkineticweatherapp.gps


interface IGPSLocation {
    fun getGPSLocation()
    fun unRegisterGPSLocationListeners()
    fun registerFunctionToGetResults(myFunction: (GpsCoordinates) -> Unit)
}
