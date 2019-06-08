package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("links")
    val links: List<Any?>?,
    @SerializedName("start")
    val start: Int?
)