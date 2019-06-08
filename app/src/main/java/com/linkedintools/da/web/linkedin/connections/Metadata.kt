package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("id")
    val id: String?,
    @SerializedName("type")
    val type: String?
)