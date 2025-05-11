package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleInfo(
    @SerialName("buyLink")
    val buyLink: String? = "",
    @SerialName("country")
    val country: String? = "",
    @SerialName("isEbook")
    val isEbook: Boolean? = false,
    @SerialName("listPrice")
    val listPrice: ListPrice? = ListPrice(),
    @SerialName("offers")
    val offers: List<Offer>? = listOf(),
    @SerialName("retailPrice")
    val retailPrice: RetailPriceX? = RetailPriceX(),
    @SerialName("saleability")
    val saleability: String? = ""
)