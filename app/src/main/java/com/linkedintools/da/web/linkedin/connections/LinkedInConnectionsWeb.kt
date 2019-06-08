package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class LinkedInConnectionsWeb(
    @SerializedName("elements")
    val elements: List<Element?>?,
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("paging")
    val paging: Paging?
)