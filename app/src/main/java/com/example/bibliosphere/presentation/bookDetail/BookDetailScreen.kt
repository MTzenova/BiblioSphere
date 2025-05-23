package com.example.bibliosphere.presentation.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.components.BookDescription
import com.example.bibliosphere.presentation.components.BookDetailCard
import com.example.bibliosphere.presentation.components.BookStateButtons

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
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                BookDetailCard(
                    author = it.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido",
                    title = it.volumeInfo?.title?:"Titulo desconocido",
                    image = it.volumeInfo?.imageLinks?.thumbnail?:"",
                    type = it.volumeInfo?.categories?.joinToString(", ") ?: "Sin categoría",
                )
                Text(text = "Descripción:", modifier = Modifier.padding(horizontal = 15.dp))
                Spacer(modifier = Modifier.height(10.dp))
                BookDescription(
                    description =  it.volumeInfo?.description ?: "Sin sinopsis disponible."
                )
            }
        }
    }
}