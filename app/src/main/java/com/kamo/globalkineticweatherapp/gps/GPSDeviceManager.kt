package com.kamo.globalkineticweatherapp.gps

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Result
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class GPSDeviceManager @Inject constructor  ( @ActivityContext private val appCompatActivity: Activity): IGPSDeviceManager {

    private var googleApiClient: GoogleApiClient? = null
    private var manager: LocationManager? = null

    init {
        manager = appCompatActivity
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun isGPSOn()=manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

    override fun turnOnGPS() {
        googleApiClientInit()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (30 * 1000).toLong()
        locationRequest.fastestInterval = (5 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(locationResultCallback)
    }

    private var locationResultCallback = ResultCallback<Result> { result->
        val status = result.status
        when (status.statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                appCompatActivity.startIntentSenderForResult(status.resolution?.intentSender, REQUEST_LOCATION, null, 0, 0, 0, null);
            } catch (e: IntentSender.SendIntentException) {
            }

        }
    }

    private fun googleApiClientInit() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(appCompatActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(bundle: Bundle?) {}

                    override fun onConnectionSuspended(i: Int) {
                        googleApiClient!!.connect()
                    }
                })
                .addOnConnectionFailedListener { connectionResult ->
                    Log.d(
                        "Location error", "Location error " + connectionResult.errorCode
                    )
                }.build()
            googleApiClient!!.connect()
        }

    }

    companion object{
        internal const val REQUEST_LOCATION = 100
    }

}
