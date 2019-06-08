package com.linkedintools.di.modules

import com.linkedintools.bo.SessionBO
import dagger.Module
import dagger.Provides

@Module
object SessionBOModule {
    @JvmStatic
    @Provides
    fun provideSessionBO() : SessionBO {
        return SessionBO()
    }
}