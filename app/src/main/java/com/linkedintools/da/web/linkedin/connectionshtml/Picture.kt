package com.linkedintools.da.web.linkedin.connectionshtml


import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("artifacts")
    val artifacts: List<ArtifactX?>?,
    @SerializedName("rootUrl")
    val rootUrl: String?
)