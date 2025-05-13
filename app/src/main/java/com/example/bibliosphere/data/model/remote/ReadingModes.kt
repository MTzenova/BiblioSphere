package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class ReadingModes(
    @SerializedName("image")
    val image: Boolean? = null,
    @SerializedName("text")
    val text: Boolean? = null
)