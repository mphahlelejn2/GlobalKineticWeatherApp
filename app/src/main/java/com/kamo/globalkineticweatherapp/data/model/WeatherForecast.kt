package com.kamo.globalkineticweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("dt_txt") val dt_txt : String,
    @SerializedName("main") val main : Main,
    @SerializedName("weather") val weather : List<Weather>
)