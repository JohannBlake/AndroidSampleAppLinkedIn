package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class Element(
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("entityUrn")
    val entityUrn: String?,
    @SerializedName("miniProfile")
    val miniProfile: MiniProfile?
)