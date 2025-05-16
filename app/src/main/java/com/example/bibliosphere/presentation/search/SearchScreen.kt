package com.example.bibliosphere.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.components.ItemBookList
import com.example.bibliosphere.presentation.components.TextInputField


@Composable
fun SearchScreen(viewModel: SearchScreenViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        //campo de búsqueda como un searchbar
        TextInputField(
            label = "Buscar libro",
            value = query,
            onValueChange = { viewModel.onQueryChange(it) }
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

        LazyColumn {
            items(books) { item ->
                ItemBookList(
                    author = item.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido",
                    title = item.volumeInfo?.title ?: "Sin título",
                    image = item.volumeInfo?.imageLinks ?: ImageLinks(thumbnail = ""),
                    onClick = {
                        item.id?.let { id ->
                            navController.navigate(BookDetail.bookRoute(id))
                        }
                    }
                )
            }
        }
    }
}

