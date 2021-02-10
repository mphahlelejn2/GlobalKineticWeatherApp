package com.kamo.globalkineticweatherapp.gps

interface IGPSDeviceManager {
    fun turnOnGPS()
    fun isGPSOn(): Boolean
}
