package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class ComlinkedincommonVectorImage(
    @SerializedName("artifacts")
    val artifacts: List<Artifact?>?,
    @SerializedName("rootUrl")
    val rootUrl: String?
)