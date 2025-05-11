package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadingModes(
    @SerialName("image")
    val image: Boolean? = null,
    @SerialName("text")
    val text: Boolean? = null
)