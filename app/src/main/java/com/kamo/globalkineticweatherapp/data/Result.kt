package com.kamo.globalkineticweatherapp.data

import java.lang.Exception

sealed class Result<out R>{
    object Loading: Result<Nothing>()
    data class Success<out T>(val data:T): Result<T>()
    data class Error(val message: String, val cause: Exception? = null) : Result<Nothing>()
}