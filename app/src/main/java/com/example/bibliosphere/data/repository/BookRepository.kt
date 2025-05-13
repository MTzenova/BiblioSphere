package com.example.bibliosphere.data.repository

import com.example.bibliosphere.data.model.remote.VolumeInfo
import com.example.bibliosphere.data.network.GoogleBooksApiService
import jakarta.inject.Inject

class BookRepository @Inject constructor(
    private val bookRepository: GoogleBooksApiService
) {
    suspend fun getVolumeInfo(): VolumeInfo {
        return bookRepository.searchBooks()
    }
}