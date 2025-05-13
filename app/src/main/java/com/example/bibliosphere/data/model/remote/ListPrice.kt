package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class ListPrice(
    @SerializedName("amount")
    val amount: Double? = null,
    @SerializedName("currencyCode")
    val currencyCode: String? = null
)