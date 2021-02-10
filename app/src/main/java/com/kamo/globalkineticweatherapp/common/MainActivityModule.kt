package com.kamo.globalkineticweatherapp.common

import android.app.Activity
import android.app.Application
import com.kamo.globalkineticweatherapp.data.local.LocalDataSource
import com.kamo.globalkineticweatherapp.data.local.LocalDataSourceImpl
import com.kamo.globalkineticweatherapp.gps.GPSDeviceManager
import com.kamo.globalkineticweatherapp.gps.IGPSDeviceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    fun provideGPSDeviceManager(appCompatActivity: Activity): IGPSDeviceManager = GPSDeviceManager(appCompatActivity)

}