package com.linkedintools.di.modules

import com.linkedintools.bo.SessionBO
import com.linkedintools.ui.main.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun providesViewModelFactory(sessionBO: SessionBO): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(sessionBO)
    }
}