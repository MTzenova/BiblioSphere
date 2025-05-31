package com.example.bibliosphere.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.bibliosphere.R
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.components.GenreList
import com.example.bibliosphere.presentation.components.ItemBookList
import com.example.bibliosphere.presentation.components.textField.TextInputField
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val booksState by viewModel.booksState.collectAsState()
    var selectedGenre by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if(viewModel.query.value.isNotBlank()) {
            viewModel.searchBooks()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = BiblioSphereTheme.dimens.paddingMedium,
                start = BiblioSphereTheme.dimens.paddingMedium,
                top = BiblioSphereTheme.dimens.paddingMedium
            )
    ) {

        //campo de búsqueda como un searchbar
        TextInputField(
            label = stringResource(id = R.string.search_book),
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            onImeAction = { viewModel.searchBooks() }
        )

        GenreList(
            genreSelected = selectedGenre,
            onGenreSelected = { genre ->
                println("Género seleccionado: $genre")
                selectedGenre = genre
                if (genre != null) {
                    //viewModel.searchBooksByGenre(genre)
                    viewModel.searchBooksByGenre(genre)
                } else {
                    viewModel.searchBooks() //no hace nada
                }
            }
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn { //cambié el de Item por BookUI porque no me actualizaba bien la lista
                items(booksState) { itemBookUI ->
//                val bookId = item.id ?: ""
//                val bookState = booksState.find { it.id == bookId }?.states?: emptySet()
                    val book = books.find { it.id == itemBookUI.id }

                    book?.let { item ->
                        ItemBookList(
                            author = item.volumeInfo?.authors?.joinToString(", ")
                                ?: stringResource(id = R.string.unknown_author),
                            title = item.volumeInfo?.title ?: stringResource(id = R.string.no_title),
                            image = item.volumeInfo?.imageLinks ?: ImageLinks(thumbnail = ""),
                            onClick = {
                                item.id?.let { id ->
                                    navController.navigate(BookDetail.bookRoute(itemBookUI.id))
                                }
                            },
                            type = item.volumeInfo?.categories?.joinToString(", ")
                                ?: stringResource(id = R.string.no_category),
                            initialStates = itemBookUI.states,
                            onStatesChanged = { newState ->
                                if (newState.isEmpty()) {
                                    viewModel.deleteBookFromLibrary(itemBookUI.id)
                                } else {
                                    viewModel.updateBookState(newState, itemBookUI.id)
                                }
                            }
                        )
                    }
                }
            }
        }


    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SearchScreenPreview(){
//    SearchScreen(viewModel = SearchScreenViewModel(), navController = rememberNavController())
//}