package com.kamo.globalkineticweatherapp.data

import androidx.lifecycle.Observer

open class Event<out T>(val data: T) {

    var hasBeenHandled = false
        private set

    fun getEventContent(): T? {
        if (hasBeenHandled)
            return null
        else {
            hasBeenHandled = true
            return data
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getEventContent()?.let {
            onEventUnhandledContent(it)
        }
    }
}