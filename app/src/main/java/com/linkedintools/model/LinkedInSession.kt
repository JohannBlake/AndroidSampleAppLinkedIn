package com.linkedintools.model

import androidx.room.Entity

@Entity
data class LinkedInSession(
    val csrfToken: String, // This is the same as JSESSIONID
    val cookie: String
)