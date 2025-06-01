package com.example.bibliosphere.presentation.library.myLibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.presentation.components.BookCover
import com.example.bibliosphere.presentation.viewmodel.MyLibraryScreenViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.components.BookShelfSeparator
import com.example.bibliosphere.presentation.components.BookStatusFilter
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun MyLibraryScreen(userId: String, viewModel: MyLibraryScreenViewModel, navController: NavController) {

    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var filterSelected by remember  { mutableStateOf<BookState?>(null) }

    LaunchedEffect(userId) {
        viewModel.getUserBooks(userId)
    }

    //poner un loading
    if(isLoading) { //lo malo es que al vovler atrás vuelve a ponerse a cargar
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           CircularProgressIndicator()
        }

    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
        )
        {
            // Textura del fondo
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.wood_texture)
                .size(600)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = stringResource(R.string.shelf_texture),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

            Column{

                BookStatusFilter(
                    statusSelected = filterSelected?.name,
                    onSatusSelected = { status ->
                        filterSelected = BookState.entries.find { it.name == status }
                        if(status != null){
                            viewModel.filterStatusBooks(filterSelected,userId)
                        }else{
                            viewModel.getUserBooks(userId)
                        }

                    }
                )

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerNormal),
                    verticalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerNormal),
                    contentPadding = PaddingValues(BiblioSphereTheme.dimens.paddingMedium),
                ){
                    items(books) { book ->
                        BookCover(book = book, onClick = {
                            book.id.let { id ->
                                navController.navigate(BookDetail.bookRoute(id))
                            }
                        })
                    }
                }
            }



        }
    }
}