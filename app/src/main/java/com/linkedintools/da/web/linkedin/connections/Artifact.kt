package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class Artifact(
    @SerializedName("expiresAt")
    val expiresAt: Long?,
    @SerializedName("fileIdentifyingUrlPathSegment")
    val fileIdentifyingUrlPathSegment: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("width")
    val width: Int?
)