package com.linkedintools.di.components

import com.linkedintools.da.web.utils.LinkedInConverters
import com.linkedintools.di.modules.LinkedInUserSettingsModule
import dagger.Component
import javax.inject.Singleton

/**
 * Used when accessing a user's LinkedIn settings page.
 */
@Singleton
@Component(modules = [(LinkedInUserSettingsModule::class)])
interface LinkedInUserSettingsComponent {
    fun inject(htmlConverter: LinkedInConverters.HtmlConverter)
}