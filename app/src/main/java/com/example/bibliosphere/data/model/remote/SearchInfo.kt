package com.example.bibliosphere.data.model.remote


import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("textSnippet")
    val textSnippet: String? = null
)