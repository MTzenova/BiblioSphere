package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("accessInfo")
    val accessInfo: AccessInfo? = AccessInfo(),
    @SerializedName("etag")
    val etag: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("kind")
    val kind: String? = "",
    @SerializedName("saleInfo")
    val saleInfo: SaleInfo? = SaleInfo(),
    @SerializedName("searchInfo")
    val searchInfo: SearchInfo? = SearchInfo(),
    @SerializedName("selfLink")
    val selfLink: String? = "",
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo? = VolumeInfo()
)