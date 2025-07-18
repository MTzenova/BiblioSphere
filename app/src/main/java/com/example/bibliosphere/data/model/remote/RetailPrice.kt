package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class RetailPrice(
    @SerializedName("amountInMicros")
    val amountInMicros: Int? = null,
    @SerializedName("currencyCode")
    val currencyCode: String? = null
)