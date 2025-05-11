package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessInfo(
    @SerialName("accessViewStatus")
    val accessViewStatus: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("embeddable")
    val embeddable: Boolean? = null,
    @SerialName("epub")
    val epub: Epub? = null,
    @SerialName("pdf")
    val pdf: Pdf? = null,
    @SerialName("publicDomain")
    val publicDomain: Boolean? = null,
    @SerialName("quoteSharingAllowed")
    val quoteSharingAllowed: Boolean? = null,
    @SerialName("textToSpeechPermission")
    val textToSpeechPermission: String? = null,
    @SerialName("viewability")
    val viewability: String? = null,
    @SerialName("webReaderLink")
    val webReaderLink: String? = null
)