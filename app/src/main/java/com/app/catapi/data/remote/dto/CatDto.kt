package com.app.catapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("url")
    val src: String
)
