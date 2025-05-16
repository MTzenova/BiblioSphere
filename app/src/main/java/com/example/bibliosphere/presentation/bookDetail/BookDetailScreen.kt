package com.example.bibliosphere.presentation.bookDetail

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.bibliosphere.presentation.components.BookDetailCard

@Composable
fun BookDetailScreen(bookId: String, viewModel: BookDetailScreenViewModel) {
    val bookDetail by viewModel.bookDetail.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBookDetail(bookId)
    }

    if (loading) {
        CircularProgressIndicator()
    }else if(errorMessage != null) {
        Text(text = errorMessage!!)
    }else{
        bookDetail?.let {
            BookDetailCard(
                author = it.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido",
                title = it.volumeInfo?.title?:"Unknown",
                image = it.volumeInfo?.imageLinks?.thumbnail?:""
            )
        }
    }
}