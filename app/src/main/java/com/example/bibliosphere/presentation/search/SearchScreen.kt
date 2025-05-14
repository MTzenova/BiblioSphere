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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bibliosphere.core.navigation.TopBar
import com.example.bibliosphere.data.network.RetrofitModule
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun SearchScreen(viewModel: SearchScreenViewModel) {
    val query by viewModel.query.collectAsState()
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

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

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        error?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn {
            items(books) { item ->
                BookItem(item)
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchScreen(drawerState: DrawerState, scope: CoroutineScope) {
//    var query by remember { mutableStateOf("") }
//    var books by remember { mutableStateOf<List<Item>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(false) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//
//    Scaffold(
//        topBar = {
//            TopBar(
//                scrollBehavior = scrollBehavior,
//                currentRoute = "search",
//                drawerState = drawerState,
//                scope = scope,
//                isSearchScreen = true,
//                searchQuery = query,
//                onSearchQueryChange = { query = it },
//                onSearch = {
//                    isLoading = true
//                    error = null
//                    CoroutineScope(Dispatchers.IO).launch {
//                        try {
//                            val response = RetrofitModule.api.searchBooks(query)
//                            withContext(Dispatchers.Main) {
//                                books = response.items ?: emptyList()
//                                isLoading = false
//                            }
//                        } catch (e: Exception) {
//                            withContext(Dispatchers.Main) {
//                                error = e.message
//                                isLoading = false
//                            }
//                        }
//                    }
//                }
//            )
//        },
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            when {
//                isLoading -> CircularProgressIndicator()
//                error != null -> Text("Error: $error", color = Color.Red)
//                else -> LazyColumn {
//                    items(books) { book -> BookItem(book) }
//                }
//            }
//        }
//    }
//}
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
