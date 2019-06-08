package com.linkedintools.di.modules

import com.linkedintools.da.local.room.RoomDB
import com.linkedintools.da.local.room.RoomDao
import dagger.Module
import dagger.Provides

@Module
class RoomDaoModule {
    @Provides
    fun providesRoomDao(): RoomDao {
        return RoomDB.getInstance().roomDao()
    }
}