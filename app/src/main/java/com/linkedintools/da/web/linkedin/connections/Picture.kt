package com.linkedintools.da.web.linkedin.connections


import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("com.linkedin.common.VectorImage")
    val comlinkedincommonVectorImage: ComlinkedincommonVectorImageX?
)