package com.kamo.globalkineticweatherapp.weatherForecast

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.kamo.globalkineticweatherapp.R
import com.kamo.globalkineticweatherapp.common.AppConstants.API_KEY
import com.kamo.globalkineticweatherapp.common.ViewModelFactory
import com.kamo.globalkineticweatherapp.common.isInternetConnected
import com.kamo.globalkineticweatherapp.data.Repository
import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.databinding.MainActivityBinding
import com.kamo.globalkineticweatherapp.gps.BaseGPSActivity
import com.kamo.globalkineticweatherapp.gps.GpsCoordinates
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseGPSActivity() , LifecycleObserver {

    @Inject
    lateinit var repository: Repository

    var gpsCoordinates: GpsCoordinates?=null

    private val weatherForecastViewModel by viewModels<WeatherForecastViewModel> {
        ViewModelFactory.getInstance(repository)
    }

    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        weatherForecastViewModel.getWeatherForecasts()
        observer()
    }

    private fun initBinding(){
        mainActivityBinding= DataBindingUtil.setContentView(this,
            R.layout.main_activity
        )
        mainActivityBinding.viewModel=weatherForecastViewModel
    }

    private fun observer() {

        gpsViewModel.locationFinderGPSCoordinates.observe(this, Observer {
            if(gpsCoordinates==null)
            {it?.let {
                loadData(it) }
            }
        })

        weatherForecastViewModel.refreshEvent.observe(this, Observer {
            if(!isInternetConnected())
                showError(getString(R.string.please_check_your_network_connection))

            loadData(gpsCoordinates)
        })

        weatherForecastViewModel.allWeatherForecasts.observe(this, Observer {

            when (it) {
                is Result.Loading -> {
                    showDialog()
                }
                is Result.Error -> {
                    showError(it.message)
                    hideDialog()
                }
                is Result.Success -> {
                    mainActivityBinding.data=it.data
                    hideDialog()
                }
            }
        })
    }


    private fun loadData(gpsCoordinates: GpsCoordinates?) {
        if(gpsCoordinates!=null){
            this.gpsCoordinates=gpsCoordinates
            weatherForecastViewModel.refresh(API_KEY,gpsCoordinates)
          }else {
              showError(getString(R.string.no_lat_and_long))
          }
    }
}