package com.example.bibliosphere.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    private const val BASE_URL = "https://www.googleapis.com/books/v1/"
    private const val API_KEY = "AIzaSyBF7rERYx2M8miJEyYZlxDFjSywpLhnHmU"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(API_KEY))
        .build()

    val api:GoogleBooksApiService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApiService::class.java)
    }
}