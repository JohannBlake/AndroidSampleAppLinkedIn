package com.linkedintools.di.modules

import com.linkedintools.da.Repository
import com.linkedintools.da.local.SharedPrefs
import com.linkedintools.da.local.room.EntityMapper
import com.linkedintools.da.local.room.RoomDao
import com.linkedintools.da.web.LinkedInAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LinkedInAPIModule::class, SharedPrefsModule::class, RoomDaoModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryImpl(sharedPrefs: SharedPrefs, linkedInApi: LinkedInAPI, roomDao: RoomDao, mapper: EntityMapper): Repository {
        return Repository(sharedPrefs, linkedInApi, roomDao, EntityMapper())
    }
}