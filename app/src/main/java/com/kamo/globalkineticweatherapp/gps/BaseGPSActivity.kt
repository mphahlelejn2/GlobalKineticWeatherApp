package com.kamo.globalkineticweatherapp.gps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.kamo.globalkineticweatherapp.R
import com.kamo.globalkineticweatherapp.base.BaseActivity
import com.kamo.globalkineticweatherapp.common.ViewModelFactory
import javax.inject.Inject

abstract class BaseGPSActivity : BaseActivity() {

    @Inject
    lateinit var fuseLocation: IGPSLocation

    @Inject
    lateinit var locationFinder: IGPSLocation

    @Inject
    lateinit var deviceManager: IGPSDeviceManager

    protected val gpsViewModel by viewModels<GpsViewModel> {
        ViewModelFactory.getInstance(
            deviceManager,
            fuseLocation,
            locationFinder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gpsObserve()
        gpsViewModel.requestGPSPermissionsEvent()
    }

    private fun gpsObserve() {
        gpsViewModel.requestGPSPermissionsEvent.observe(this, Observer {
            requestGPSPermissions()
        })
    }

    private fun turnOnGPS() {
        gpsViewModel.turnOnGPS()
    }

    private fun permissionsToast() {
        Toast.makeText(this,
            getString(R.string.permissions_granted), Toast.LENGTH_SHORT).show()
    }

    private fun gpsTurnedOnToast() {
        Toast.makeText(this,
            getString(R.string.gps_turned_on), Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    protected fun requestGPSPermissions(){
        val permissionGranted1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val permissionGranted2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
        if (!permissionGranted1 || !permissionGranted2) {
            requestPermissions(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),200)
            displayToastMsg(getString(R.string.gps_permissions_off))
        }else {
            if (gpsViewModel.isGPSDeviceOn()) {
                gpsViewModel.requestGPSCoordinates()
            } else
                turnOnGPS()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            200 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsToast()
                    turnOnGPS()
                } else {
                    displayToastMsg(resources.getString(R.string.Permission_denied))
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GPSDeviceManager.REQUEST_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                gpsTurnedOnToast()
                gpsViewModel.requestGPSCoordinates()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                displayToastMsg(getString(R.string.Permission_denied))
            }
        }
    }

}