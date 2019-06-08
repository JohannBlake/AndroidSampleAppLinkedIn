package com.linkedintools.di.components

import com.linkedintools.di.modules.NotificationsModule
import com.linkedintools.di.modules.RepositoryModule
import com.linkedintools.service.LinkedInService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NotificationsModule::class), (RepositoryModule::class)])
interface ServiceDependenciesComponent {
    fun inject(linkedInService: LinkedInService)
}