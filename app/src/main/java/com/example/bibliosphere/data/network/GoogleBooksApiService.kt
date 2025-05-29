package com.example.bibliosphere.data.network

import com.example.bibliosphere.data.model.remote.BookDto
import com.example.bibliosphere.data.model.remote.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes") //para realizar búsqueda de volúmenes, el endpoint
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apikey: String,
        @Query("maxResults") maxResults: Int = 40,
    ): BookDto

    @GET("volumes/{id}") //para conseguir el id de un libro, endpoint
    suspend fun getBookDetail(
        @Path("id") id: String
    ): Item
}