package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.firebase.BookData

data class BookFromFirestore(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val imageLinks: ImageLinks? = null,
    val status: String = ""
)

@Composable
fun BookCover(
    book: BookData,
    onClick: () -> Unit
) {
//    val imageUrl = book.volumeInfo?.imageLinks?.thumbnail?.replace("http", "https") ?: ""
    val imageUrl = book.thumbnail.replace("http", "https")
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 180.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .shadow(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ){
        Image(
            painter = rememberAsyncImagePainter( model = imageUrl),
            contentDescription = stringResource(R.string.book_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BookShelfSeparator() {
    Surface(
        shadowElevation = 18.dp,
        tonalElevation = 50.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
            ),
        shape = RectangleShape,
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.wood_texture)
                .size(252)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.bookshelf_separator),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = 0.3f),
                blendMode = BlendMode.Darken
            )
        )
    }
}
