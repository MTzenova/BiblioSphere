package com.example.bibliosphere.data.model

data class BookUI(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val thumbnailUrl: String,
    val publisher: String = "",
    val publishedDate: String = "",
    val pageCount: Int = 0,
    val categories: String = "",
    val previewLink: String = ""
)