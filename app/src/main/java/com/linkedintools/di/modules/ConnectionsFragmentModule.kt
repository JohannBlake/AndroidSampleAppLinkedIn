package com.linkedintools.di.modules

import com.linkedintools.bo.ConnectionsBO
import com.linkedintools.ui.main.fragments.connections.ConnectionsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ConnectionsFragmentModule {
    @Provides
    fun providesViewModelFactory(connectionsBO: ConnectionsBO): ConnectionsViewModelFactory {
        return ConnectionsViewModelFactory(connectionsBO)
    }
}