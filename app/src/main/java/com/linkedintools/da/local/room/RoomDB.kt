package com.linkedintools.da.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.linkedintools.App

@Database(entities = arrayOf(LinkedInConnectionEntity::class), version = 1, exportSchema = true)
@TypeConverters(RoomConverters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        fun getInstance(): RoomDB =
            Room.databaseBuilder(App.context, RoomDB::class.java, "LinkedinToolsDB")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {

                    }
                }).build()
    }
}