package com.linkedintools.di.components

import com.linkedintools.App
import com.linkedintools.di.modules.ServiceUtilModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ServiceUtilModule::class)])
interface AppComponent {
    fun inject(app: App)
}