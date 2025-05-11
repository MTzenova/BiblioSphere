package com.example.bibliosphere.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    @SerialName("allowAnonLogging")
    val allowAnonLogging: Boolean? = null,
    @SerialName("authors")
    val authors: List<String?>? = null,
    @SerialName("averageRating")
    val averageRating: Double? = null,
    @SerialName("canonicalVolumeLink")
    val canonicalVolumeLink: String? = null,
    @SerialName("categories")
    val categories: List<String?>? = null,
    @SerialName("contentVersion")
    val contentVersion: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("imageLinks")
    val imageLinks: ImageLinks? = null,
    @SerialName("industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifier?>? = null,
    @SerialName("infoLink")
    val infoLink: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("maturityRating")
    val maturityRating: String? = null,
    @SerialName("pageCount")
    val pageCount: Int? = null,
    @SerialName("panelizationSummary")
    val panelizationSummary: PanelizationSummary? = null,
    @SerialName("previewLink")
    val previewLink: String? = null,
    @SerialName("printType")
    val printType: String? = null,
    @SerialName("publishedDate")
    val publishedDate: String? = null,
    @SerialName("publisher")
    val publisher: String? = null,
    @SerialName("ratingsCount")
    val ratingsCount: Int? = null,
    @SerialName("readingModes")
    val readingModes: ReadingModes? = null,
    @SerialName("subtitle")
    val subtitle: String? = null,
    @SerialName("title")
    val title: String? = null
)