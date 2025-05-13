package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class IndustryIdentifier(
    @SerializedName("identifier")
    val identifier: String? = null,
    @SerializedName("type")
    val type: String? = null
)