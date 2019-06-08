package com.linkedintools.model

import java.util.*

data class LinkedInUserSettings(
    var id: Int?,
    var profileUrl: String?,
    var photoUrl: String?,
    var name: String?,
    var title: String?,
    var memberSince: Date?


) {
    constructor() : this(null, null, null, null, null, null)
}