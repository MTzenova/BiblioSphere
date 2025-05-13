package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("items")
    val items: List<Item>? = listOf(),
    @SerializedName("kind")
    val kind: String? = "",
    @SerializedName("totalItems")
    val totalItems: Int? = 0
)