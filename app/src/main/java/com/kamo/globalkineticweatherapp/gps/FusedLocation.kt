
package com.kamo.globalkineticweatherapp.gps

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class FusedLocation @Inject constructor (val context: Application): IGPSLocation {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    lateinit var myFunction:(GpsCoordinates)->Unit

    override fun getGPSLocation(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let {
                    val gps= GpsCoordinates(latitude = location.latitude,longitude = location.longitude,accuracy = location.accuracy,
                            altitude=location.altitude)
                    myFunction(gps)
                }
            }
    }

    override fun registerFunctionToGetResults(myFunction:(GpsCoordinates)->Unit) {
        this.myFunction=myFunction
    }

    override fun unRegisterGPSLocationListeners() {
    }

}