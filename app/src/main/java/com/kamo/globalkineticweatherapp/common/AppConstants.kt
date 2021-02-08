package com.kamo.globalkineticweatherapp.common

import androidx.databinding.library.BuildConfig
import com.kamo.globalkineticweatherapp.common.AppConstants.API_KEY


object AppConstants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.openweathermap.org/"
    const val LAT="lat"
    const val LONG="long"
}