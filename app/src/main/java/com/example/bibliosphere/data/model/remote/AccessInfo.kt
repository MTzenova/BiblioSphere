package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class AccessInfo(
    @SerializedName("accessViewStatus")
    val accessViewStatus: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("embeddable")
    val embeddable: Boolean? = null,
    @SerializedName("epub")
    val epub: Epub? = null,
    @SerializedName("pdf")
    val pdf: Pdf? = null,
    @SerializedName("publicDomain")
    val publicDomain: Boolean? = null,
    @SerializedName("quoteSharingAllowed")
    val quoteSharingAllowed: Boolean? = null,
    @SerializedName("textToSpeechPermission")
    val textToSpeechPermission: String? = null,
    @SerializedName("viewability")
    val viewability: String? = null,
    @SerializedName("webReaderLink")
    val webReaderLink: String? = null
)