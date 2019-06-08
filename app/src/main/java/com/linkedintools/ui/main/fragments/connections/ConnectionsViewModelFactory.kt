package com.linkedintools.ui.main.fragments.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedintools.bo.ConnectionsBO

class ConnectionsViewModelFactory(
    private val connectionsBO: ConnectionsBO
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConnectionsViewModel(connectionsBO) as T
    }
}