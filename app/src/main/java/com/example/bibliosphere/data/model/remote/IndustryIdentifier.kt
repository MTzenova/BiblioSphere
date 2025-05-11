package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndustryIdentifier(
    @SerialName("identifier")
    val identifier: String? = null,
    @SerialName("type")
    val type: String? = null
)