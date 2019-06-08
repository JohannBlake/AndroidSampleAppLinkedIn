package com.linkedintools.di.modules

import com.linkedintools.bo.ConnectionsBO
import dagger.Module
import dagger.Provides

@Module
object ConnectionsBOModule {
    @JvmStatic
    @Provides
    fun provideConnectionsBO() : ConnectionsBO {
        return ConnectionsBO()
    }
}