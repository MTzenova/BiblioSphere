package com.example.bibliosphere.presentation.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bibliosphere.data.model.remote.Item
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import com.example.bibliosphere.presentation.components.ItemBookList


@Composable
fun SearchScreen(viewModel: SearchScreenViewModel) {
    val query by viewModel.query.collectAsState()
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        // Campo de búsqueda
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Buscar libro") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de búsqueda
        Button(
            onClick = { viewModel.searchBooks() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        error?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn {
            items(books) { item ->
                ItemBookList(
                    author = item.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido",
                    title = item.volumeInfo?.title ?: "Sin título",
                    image = item.volumeInfo?.imageLinks ?: ImageLinks(thumbnail = "")
                )
            }
        }
    }
}

//@Composable
//fun BookItem(book: Item) {
//    val title = book.volumeInfo?.title ?: "Sin título"
//    val authors = book.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido"
//    val imageUrl = book.volumeInfo?.imageLinks?.thumbnail?.replace("http", "https")
//
//    Row(
//        modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(20.dp))
//            .background(MaterialTheme.colorScheme.inversePrimary)
//            .padding(16.dp)
//    ) {
//        imageUrl?.let {
//            Log.d("SearchScreen", "Imagen: $imageUrl")
//
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = null,
//                modifier = Modifier.size(80.dp),
//            )
//        }
//        Spacer(modifier = Modifier.width(8.dp))
//        Column {
//            Text(title, fontWeight = FontWeight.Bold)
//            Text(authors)
//        }
//    }
//    Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
//}
