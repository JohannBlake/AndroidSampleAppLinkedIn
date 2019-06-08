package com.linkedintools.di.modules

import com.linkedintools.ui.utils.Notifications
import dagger.Module
import dagger.Provides

@Module
object NotificationsModule {
    @JvmStatic
    @Provides
    fun provideNotifications() : Notifications {
        return Notifications()
    }
}