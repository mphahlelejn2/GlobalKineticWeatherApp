package com.kamo.globalkineticweatherapp.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kamo.globalkineticweatherapp.R
import com.kamo.globalkineticweatherapp.common.isInternetConnected
import dmax.dialog.SpotsDialog


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var spotDialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestInternetPermission()
        spotDialog= SpotsDialog(this,resources.getString(R.string.Loading_data))
    }

    override fun onStart() {
        super.onStart()

        if (!isInternetConnected())
            displayToastMsg(resources.getString(R.string.no_internet_connection))
    }

    private fun requestInternetPermission() {
        val permissionGranted1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
        val permissionGranted2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted1 || !permissionGranted2) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE),
                    1
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                } else {
                    displayToastMsg(resources.getString(R.string.Permission_denied))
                    this.finish()
                }
                return
            }
        }
    }

    protected fun showDialog() {
        if(!spotDialog.isShowing)
            spotDialog.show()
    }

    protected fun hideDialog() {
        if(spotDialog.isShowing)
            spotDialog.dismiss()
    }

    protected fun displayToastMsg(string: String) {
        Toast.makeText(this,string, Toast.LENGTH_SHORT).show()
    }

    protected fun showError(str:String) {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }


}