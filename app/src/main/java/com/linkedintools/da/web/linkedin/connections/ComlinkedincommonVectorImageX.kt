package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class ComlinkedincommonVectorImageX(
    @SerializedName("artifacts")
    val artifacts: List<ArtifactX?>?,
    @SerializedName("rootUrl")
    val rootUrl: String?
)