package com.example.bibliosphere.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.components.ItemBookList
import com.example.bibliosphere.presentation.components.textField.TextInputField

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val booksState by viewModel.booksState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        //campo de búsqueda como un searchbar
        TextInputField(
            label = "Buscar libro",
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            onImeAction = {viewModel.searchBooks()}
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        LazyColumn { //cambié el de Item por BookUI porque no me actualizaba bien la lista
            items(booksState) { itemBookUI ->
//                val bookId = item.id ?: ""
//                val bookState = booksState.find { it.id == bookId }?.states?: emptySet()
                val book = books.find{ it.id == itemBookUI.id }

                book?.let { item ->
                    ItemBookList(
                        author = item.volumeInfo?.authors?.joinToString(", ") ?: "Autor desconocido",
                        title = item.volumeInfo?.title ?: "Sin título",
                        image = item.volumeInfo?.imageLinks ?: ImageLinks(thumbnail = ""),
                        onClick = {
                            item.id?.let { id ->
                                navController.navigate(BookDetail.bookRoute(itemBookUI.id))
                            }
                        },
                        type = item.volumeInfo?.categories?.joinToString(", ") ?: "Sin categoría",
                        initialStates = itemBookUI.states,
                        onStatesChanged = { newState ->
                            viewModel.updateBookState(newState,itemBookUI.id)
                        }
                    )
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