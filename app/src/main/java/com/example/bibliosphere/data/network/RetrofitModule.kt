package com.example.bibliosphere.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    private const val BASE_URL = "https://www.googleapis.com/books/v1/"
    private const val API_KEY = "AIzaSyD3XGbuQhMUFqYs1qGEY0Wq_8Dh3_hwRqQ"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(API_KEY))
        .build()

    val api:GoogleBooksApiService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApiService::class.java)
    }
}