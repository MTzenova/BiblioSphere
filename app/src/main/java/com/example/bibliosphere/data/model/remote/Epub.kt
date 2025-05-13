package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class Epub(
    @SerializedName("acsTokenLink")
    val acsTokenLink: String? = null,
    @SerializedName("isAvailable")
    val isAvailable: Boolean? = null
)