package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class BackgroundImage(
    @SerializedName("com.linkedin.common.VectorImage")
    val comlinkedincommonVectorImage: ComlinkedincommonVectorImage?
)