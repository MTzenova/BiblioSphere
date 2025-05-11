package com.example.bibliosphere.data

import com.example.bibliosphere.data.model.remote.BookDetail
import com.example.bibliosphere.data.model.remote.Item
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apikey: String,
        @Query("maxResults") maxResults: Int = 40,
    ): BookDetail
}