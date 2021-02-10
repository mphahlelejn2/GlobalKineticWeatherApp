package com.kamo.globalkineticweatherapp.gps

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.ViewModelInject
import javax.inject.Inject

private const val MINUTES_IN_MILLI2: Long = 1000

class GPSLocationFinder @Inject constructor (private val context: Application) : LocationListener, IGPSLocation {

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    lateinit var myFunction:(GpsCoordinates)->Unit

    override fun getGPSLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINUTES_IN_MILLI2, 0f, this)
    }

    override fun unRegisterGPSLocationListeners() {
        locationManager.removeUpdates(this)
    }

    override fun registerFunctionToGetResults(myFunction:(GpsCoordinates)->Unit) {
        this.myFunction=myFunction
    }

    override fun onLocationChanged(location: Location) {
        if (location != null) {
           val coordinates=
               GpsCoordinates(longitude = location.longitude,latitude = location.latitude,accuracy = location.accuracy
           , altitude=location.altitude)
            myFunction(coordinates)
            locationManager.removeUpdates(this)
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}
}
