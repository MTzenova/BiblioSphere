package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetail(
    @SerialName("items")
    val items: List<Item>? = listOf(),
    @SerialName("kind")
    val kind: String? = "",
    @SerialName("totalItems")
    val totalItems: Int? = 0
)