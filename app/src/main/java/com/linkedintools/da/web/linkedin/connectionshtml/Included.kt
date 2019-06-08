package com.linkedintools.da.web.linkedin.connectionshtml


import com.google.gson.annotations.SerializedName

data class Included(
    @SerializedName("backgroundImage")
    val backgroundImage: BackgroundImage?,
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("entityUrn")
    val entityUrn: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("*miniProfile")
    val miniProfile: String?,
    @SerializedName("objectUrn")
    val objectUrn: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("phoneNumbers")
    val phoneNumbers: Any?,
    @SerializedName("picture")
    val picture: Picture?,
    @SerializedName("primaryEmailAddress")
    val primaryEmailAddress: Any?,
    @SerializedName("publicIdentifier")
    val publicIdentifier: String,
    @SerializedName("trackingId")
    val trackingId: String?,
    @SerializedName("twitterHandles")
    val twitterHandles: Any?,
    @SerializedName("weChatContactInfo")
    val weChatContactInfo: Any?
)