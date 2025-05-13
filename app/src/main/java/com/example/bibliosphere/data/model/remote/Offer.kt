package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("finskyOfferType")
    val finskyOfferType: Int? = 0,
    @SerializedName("giftable")
    val giftable: Boolean? = false,
    @SerializedName("listPrice")
    val listPrice: ListPriceX? = ListPriceX(),
    @SerializedName("retailPrice")
    val retailPrice: RetailPrice? = RetailPrice()
)