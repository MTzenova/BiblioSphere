package com.example.bibliosphere.presentation.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.bibliosphere.data.model.remote.Item
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchScreen() {
    var books by remember { mutableStateOf<List<Item>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            isLoading = true
            error = null

            // Llamada a la API en una corrutina
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitModule.api.searchBooks("harry potter")
                    withContext(Dispatchers.Main) {
                        books = response.items ?: emptyList()
                        isLoading = false
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        error = e.message
                        isLoading = false
                    }
                }
            }
        }) {
            Text("Buscar libros")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (error != null) {
            Text("Error: $error", color = Color.Red)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(books) { book ->
                    BookItem(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Item) {
    val title = book.volumeInfo?.title ?: "Sin título"
    val authors = book.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido"
    val imageUrl = book.volumeInfo?.imageLinks?.thumbnail?.replace("http", "https")

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(16.dp)
    ) {
        imageUrl?.let {
            Log.d("SearchScreen", "Imagen: $imageUrl")

            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold)
            Text(authors)
        }
    }
    Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
}
