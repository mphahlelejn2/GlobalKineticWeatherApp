package com.kamo.globalkineticweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("dt_txt") val dt_txt : String?=null,
    @SerializedName("main") val main : Main?=null,
    @SerializedName("weather") val weather : List<Weather>?=null
)