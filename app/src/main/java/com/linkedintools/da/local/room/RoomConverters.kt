package com.linkedintools.da.local.room

import androidx.room.TypeConverter
import java.util.*

/**
 * Handles the conversion of data types that Room does not normally support.
 */
class RoomConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}