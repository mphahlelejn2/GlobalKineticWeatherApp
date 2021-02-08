package com.kamo.globalkineticweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Weather (
	var weatherForecastDateId : Long,
	@SerializedName("id") val id : Int,
	@SerializedName("main") val main : String,
	@SerializedName("description") val description : String,
	@SerializedName("icon") val icon : String
)
