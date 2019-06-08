package com.linkedintools.da.web.linkedin.connectionshtml


import com.google.gson.annotations.SerializedName

data class LinkedInConnectionsWeb(
    @SerializedName("included")
    val included: List<Included?>?
)