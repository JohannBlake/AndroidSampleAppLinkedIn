package com.linkedintools.di.modules

import com.linkedintools.bo.TipsBO
import dagger.Module
import dagger.Provides

@Module
object TipsBOModule {
    @JvmStatic
    @Provides
    fun provideTipsBO() : TipsBO {
        return TipsBO()
    }
}