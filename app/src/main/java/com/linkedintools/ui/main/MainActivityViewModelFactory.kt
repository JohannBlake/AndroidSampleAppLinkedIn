package com.linkedintools.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedintools.bo.SessionBO

class MainActivityViewModelFactory(
    private val sessionBO: SessionBO
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(sessionBO) as T
    }
}