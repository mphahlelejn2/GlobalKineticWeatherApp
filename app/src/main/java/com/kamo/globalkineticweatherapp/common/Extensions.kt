package com.kamo.globalkineticweatherapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.isInternetConnected(): Boolean {

    val connectivity = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity == null) {
        return false
    } else {
        val info = connectivity.allNetworkInfo
        if (info != null) {
            for (i in info.indices) {
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
    }
    return false
}