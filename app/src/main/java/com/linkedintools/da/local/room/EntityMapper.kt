package com.linkedintools.da.local.room

import com.linkedintools.model.LinkedInConnection
import javax.inject.Inject

/**
 * Used to handle the transformation of data into or out of the Room database.
 */
class EntityMapper @Inject constructor() {
    fun fromDb(from: LinkedInConnectionEntity) = LinkedInConnection(
        from.id,
        from.firstName,
        from.lastName,
        from.profileUrl,
        from.occupation,
        from.entityUrn,
        from.photoUrl,
        from.dateConnected,
        from.location,
        from.companyId
    )

    fun toDb(from: LinkedInConnection) = LinkedInConnectionEntity(
        from.id,
        from.firstName,
        from.lastName,
        from.profileUrl,
        from.occupation,
        from.entityUrn,
        from.photoUrl,
        from.dateConnected,
        from.location,
        from.companyId
    )
}