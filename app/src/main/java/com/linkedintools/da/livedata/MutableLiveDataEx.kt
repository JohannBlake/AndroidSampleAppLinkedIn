package com.linkedintools.da.livedata

import androidx.lifecycle.MutableLiveData

class MutableLiveDataEx<T> : MutableLiveData<T> {
    constructor() : super()

    /**
     * This can be used when you don't want to be bothered passing data to the observer, i.e., you're only interested in letting them know
     * that an event has taken place.
     */
    fun notifyWithoutData() {
        super.postValue(null)
    }
}