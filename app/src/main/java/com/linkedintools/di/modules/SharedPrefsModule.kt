package com.linkedintools.di.modules

import com.linkedintools.da.local.SharedPrefs
import dagger.Module
import dagger.Provides

@Module
object SharedPrefsModule {
    @JvmStatic
    @Provides
    fun provideSharedPrefs() : SharedPrefs {
        return SharedPrefs()
    }
}