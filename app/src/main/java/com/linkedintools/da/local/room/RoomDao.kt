package com.linkedintools.da.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Observable


@Dao
interface RoomDao {
    @Query("SELECT * FROM LinkedInConnectionEntity")
    fun getAll(): LiveData<List<LinkedInConnectionEntity>>

    @Insert(onConflict = REPLACE)
    fun storeConnections(linkedInConnection: List<LinkedInConnectionEntity>)

    @Query("SELECT * FROM LinkedInConnectionEntity ORDER BY firstName COLLATE NOCASE ASC")
    fun getConnectionsByFirstName(): DataSource.Factory<Int, LinkedInConnectionEntity>

    @Query("SELECT COUNT(id) FROM LinkedInConnectionEntity")
    fun getConnectionsCount(): Observable<Int>
}