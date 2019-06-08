package com.linkedintools.model

import java.util.*


data class LinkedInConnection(
    val id: Int,
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