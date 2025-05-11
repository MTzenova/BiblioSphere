package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RetailPriceX(
    @SerialName("amount")
    val amount: Double? = null,
    @SerialName("currencyCode")
    val currencyCode: String? = null
)