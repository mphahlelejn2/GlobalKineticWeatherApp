package com.kamo.globalkineticweatherapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast

interface ApiService {
    @GET("data/2.5/weather")
    suspend  fun getWeatherForecastByLatAndLong(
        @Query("apikey") apikey: String?,
        @Query("lat") lat: String?,
        @Query("lon") lng: String?
    ): Response<WeatherForecast>
}
