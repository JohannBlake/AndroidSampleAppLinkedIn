package com.linkedintools.da.local.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

// The table used in the Room database to store LinkedIn connections.
@Entity(indices = arrayOf(Index(value = ["lastName"])))
data class LinkedInConnectionEntity(
    @PrimaryKey val id: Int,
    val firstName: String?,
    val lastName: String?,
    val profileUrl: String?,
    val occupation: String?,
    val entityUrn: String?,
    val photoUrl: String?,
    val dateConnected: Date?,
    val location: String?,
    val companyId: String?
)