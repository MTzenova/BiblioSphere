package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListPriceX(
    @SerialName("amountInMicros")
    val amountInMicros: Int? = null,
    @SerialName("currencyCode")
    val currencyCode: String? = null
)