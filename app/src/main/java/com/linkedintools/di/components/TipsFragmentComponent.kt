package com.linkedintools.di.components

import com.linkedintools.di.modules.TipsFragmentModule
import com.linkedintools.ui.main.fragments.tips.TipsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(TipsFragmentModule::class)])
interface TipsFragmentComponent {
    fun inject(tipsFragment: TipsFragment)
}