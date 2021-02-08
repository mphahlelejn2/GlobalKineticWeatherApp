package com.kamo.globalkineticweatherapp.data.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class MyResponse (
	@SerializedName("list") val list : List<WeatherForecast>
)
