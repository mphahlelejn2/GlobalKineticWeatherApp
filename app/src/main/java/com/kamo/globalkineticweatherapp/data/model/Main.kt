
package com.kamo.globalkineticweatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Main (
    @SerializedName("temp") var temp : Double,
    @SerializedName("humility") var humility : Double,
    @SerializedName("pressure") var pressure : Double,
    @SerializedName("temp_min") var temp_min : Double,
    @SerializedName("temp_max") var temp_max : Double
)