package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class PanelizationSummary(
    @SerializedName("containsEpubBubbles")
    val containsEpubBubbles: Boolean? = null,
    @SerializedName("containsImageBubbles")
    val containsImageBubbles: Boolean? = null
)