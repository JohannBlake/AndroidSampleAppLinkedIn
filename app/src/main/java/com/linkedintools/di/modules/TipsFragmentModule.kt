package com.linkedintools.di.modules

import com.linkedintools.bo.TipsBO
import com.linkedintools.ui.main.fragments.tips.TipsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TipsFragmentModule {
    @Provides
    fun providesViewModelFactory(tipsBO: TipsBO): TipsViewModelFactory {
        return TipsViewModelFactory(tipsBO)
    }
}