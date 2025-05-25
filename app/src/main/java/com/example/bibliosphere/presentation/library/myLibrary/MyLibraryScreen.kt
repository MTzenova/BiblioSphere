package com.example.bibliosphere.presentation.library.myLibrary

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
import androidx.compose.material3.*

@Composable
fun MyLibraryScreen(userId: String, viewModel: MyLibraryScreenViewModel, navController: NavController) {

    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUserBooks(userId)
    }

//    if(books.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ){
//            Text(text = "Aún no has añadido ningún libro a tu biblioteca")
//        }
//    }else{
//
//    }
    //poner un loading
    if(isLoading) { //lo malo es que al vovler atrás vuelve a ponerse a cargar
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           CircularProgressIndicator()
        }

    }else{
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ){
            items(books) { book ->
                BookCover(book = book, onClick = {
                    book.id?.let { id ->
                        navController.navigate(BookDetail.bookRoute(id))
                    }
                })
            }
        }
    }

}

//val isLoading by viewModel.isLoading.collectAsState()
//
//when {
//    isLoading -> {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    }
//    books.isEmpty() -> {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text(text = "Aún no has añadido ningún libro a tu biblioteca")
//        }
//    }
//    else -> {
//        LazyVerticalGrid(
//            modifier = Modifier.fillMaxSize(),
//            columns = GridCells.Fixed(3),
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            items(books) { book ->
//                BookCover(book = book, onClick = { onClick(book) })
//            }
//        }
//    }
//}