package com.linkedintools.da.web.linkedin.connectionshtml


import com.google.gson.annotations.SerializedName

data class ArtifactX(
    @SerializedName("expiresAt")
    val expiresAt: Long?,
    @SerializedName("fileIdentifyingUrlPathSegment")
    val fileIdentifyingUrlPathSegment: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("width")
    val width: Int?
)