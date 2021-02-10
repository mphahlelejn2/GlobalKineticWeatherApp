package com.kamo.globalkineticweatherapp.common

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.kamo.globalkineticweatherapp.data.Result
import com.kamo.globalkineticweatherapp.data.model.WeatherForecast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


object SharedPrefWorker {

    fun getPrefData(context: Context,key: String): Flow<Result<WeatherForecast>> = flow {

        val prefs = PreferenceManager.getDefaultSharedPreferences(context);

        emit(Result.Loading)

        val gson = Gson()
        val json: String? = prefs.getString(key, "")
        val obj=gson.fromJson(json,WeatherForecast::class.java)

        if(obj!=null)
            emit(Result.Success(obj!!))
            else
            emit(Result.Success(WeatherForecast()))

    }.flowOn(Dispatchers.IO)


    fun setPrefString(context: Context, key: String,value: WeatherForecast) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context);
        val prefsEditor = prefs.edit();
        val gson =  Gson();
        val json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

}