package com.linkedintools.ui.main

import com.linkedintools.bo.SessionBO
import com.linkedintools.da.livedata.MutableLiveDataEx
import com.linkedintools.model.LinkedInUserSettings
import com.linkedintools.ui.viewmodels.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val sessionBO: SessionBO): BaseViewModel() {

    val onFirstAppUsage: MutableLiveDataEx<Unit> = MutableLiveDataEx()

    fun getLinkedInUserSettings(): LinkedInUserSettings {
        return sessionBO.getLinkedInUserSettings()
    }

    fun notifyIfFirstAppUsage() {
        if (sessionBO.firstUsageInitialized)
            onFirstAppUsage.notifyWithoutData()
    }

    fun firstUsageInitialized() {
        sessionBO.firstUsageInitialized = true
    }

    fun signOut() {
        sessionBO.signOut()
    }
}