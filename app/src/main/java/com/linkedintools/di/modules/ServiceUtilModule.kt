package com.linkedintools.di.modules

import com.linkedintools.service.ServiceUtil
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class, NotificationsModule::class])
class ServiceUtilModule {
    @Provides
    fun provideServiceUtil() : ServiceUtil {
        return ServiceUtil()
    }
}