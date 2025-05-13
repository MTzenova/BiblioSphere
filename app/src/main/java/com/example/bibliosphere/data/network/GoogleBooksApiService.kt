package com.example.bibliosphere.data.network

import com.example.bibliosphere.data.model.remote.BookDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes") //para realizar búsqueda de volúmenes, el endpoint
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apikey: String,
        @Query("maxResults") maxResults: Int = 40,
    ): BookDto
}