package com.linkedintools.di.components

import com.linkedintools.di.modules.MainActivityModule
import com.linkedintools.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}