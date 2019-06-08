package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class MiniProfile(
    @SerializedName("backgroundImage")
    val backgroundImage: BackgroundImage?,
    @SerializedName("entityUrn")
    val entityUrn: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("objectUrn")
    val objectUrn: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("picture")
    val picture: Picture?,
    @SerializedName("publicIdentifier")
    val publicIdentifier: String?,
    @SerializedName("trackingId")
    val trackingId: String?
)