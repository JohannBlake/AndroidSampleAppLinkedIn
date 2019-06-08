package com.linkedintools.da.web.linkedin.connectionshtml


import com.google.gson.annotations.SerializedName

data class BackgroundImage(
    @SerializedName("artifacts")
    val artifacts: List<Artifact?>?,
    @SerializedName("rootUrl")
    val rootUrl: String?
)