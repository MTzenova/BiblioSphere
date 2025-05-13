package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class SaleInfo(
    @SerializedName("buyLink")
    val buyLink: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("isEbook")
    val isEbook: Boolean? = false,
    @SerializedName("listPrice")
    val listPrice: ListPrice? = ListPrice(),
    @SerializedName("offers")
    val offers: List<Offer>? = listOf(),
    @SerializedName("retailPrice")
    val retailPrice: RetailPriceX? = RetailPriceX(),
    @SerializedName("saleability")
    val saleability: String? = ""
)