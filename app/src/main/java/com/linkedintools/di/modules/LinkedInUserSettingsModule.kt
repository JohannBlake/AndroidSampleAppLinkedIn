package com.linkedintools.di.modules

import com.linkedintools.model.LinkedInUserSettings
import dagger.Module
import dagger.Provides

@Module
class LinkedInUserSettingsModule {
    @Provides
    fun provideLinkedInUserSettings() : LinkedInUserSettings {
        return LinkedInUserSettings()
    }
}