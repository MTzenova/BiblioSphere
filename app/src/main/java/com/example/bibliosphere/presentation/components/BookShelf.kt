//package com.example.bibliosphere.presentation.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.BlendMode
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberAsyncImagePainter
//import coil.request.ImageRequest
//import com.example.bibliosphere.R
//import com.example.bibliosphere.data.model.remote.ImageLinks
//
//@Composable
//fun BookShelf(
//    author:String,
//    title:String,
//    image: ImageLinks,
//    onClick:()->Unit,
//    type:String,
//){
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        //Para la textura del fondo
//        val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
//            ImageRequest.Builder(LocalContext.current)
//                .data(R.drawable.wood_texture)
//                .size(600)
//                .build()
//        )
//
//        Image(
//            painter = painter,
//            contentDescription = "Textura de la estantería",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//        Column {
//            //aqui poner los libros
//            BooksCovers(author, title, image, onClick)
//            BookShelfSeparator()
//
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MyLibraryScreenPreview() {
//    BookShelf(author = "J.K.Rowling", title = "Harry Potter", image = ImageLinks(thumbnail = "https://upload.wikimedia.org/wikipedia/en/a/a9/Harry_Potter_and_the_Goblet_of_Fire.jpg"),onClick = {},"AAAA")
//}
//
//@Composable
//fun BookShelfSeparator(){
//    Surface(
//        shadowElevation = 18.dp,
//        tonalElevation = 50.dp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(20.dp)
//            .border(
//                width = 2.dp,
//                color = Color.Black,
//            ),
//        shape = RectangleShape,
//        ) {
//        val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
//            ImageRequest.Builder(LocalContext.current)
//                .data(R.drawable.wood_texture)
//                .size(252)
//                .build()
//        )
//        Image(
//            painter = painter,
//            contentDescription = "Separador",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize(),
//            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.3f),
//                blendMode = BlendMode.Darken)
//        )
//    }
//}
//
//@Composable
//fun BooksCovers(
//    author: String,
//    title: String,
//    image: ImageLinks,
//    onClick: () -> Unit
//){
//    Card(
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { /*vamos a la pantalla de BookDetailScreen*/ },
//    ) {
//        Image(
//            painter = rememberAsyncImagePainter(model = image.thumbnail?.replace("http", "https") ?: ""),
//            contentDescription = "Book of $author",
//            modifier = Modifier
//                .size(98.dp, 145.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(MaterialTheme.colorScheme.inversePrimary)
//                .padding(8.dp)
//        )
//    }
//}
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.data.model.remote.Item
import com.example.bibliosphere.presentation.firebase.BookData

// Modelo simple para los libros desde Firestore
data class BookFromFirestore(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val imageLinks: ImageLinks? = null,
    val status: String = ""
)

@Composable
fun BookShelf(
    books: List<BookFromFirestore>,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        // Textura del fondo
//        val painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(LocalContext.current)
//                .data(R.drawable.wood_texture)
//                .size(600)
//                .build()
//        )
//
//        Image(
//            painter = painter,
//            contentDescription = "Textura de la estantería",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        // Organizar libros por estado
//        val booksByStatus = books.groupBy { it.status }
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(vertical = 16.dp)
//        ) {
//            booksByStatus.forEach { (status, booksInStatus) ->
//                item {
//                    BookShelfSection(
//                        sectionTitle = getStatusDisplayName(status),
//                        books = booksInStatus,
//                        onBookClick = onBookClick
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//            }
//        }
//    }
}

//@Composable
//fun BookShelfSection(
//    sectionTitle: String,
//    books: List<BookFromFirestore>,
//    onBookClick: (String) -> Unit
//) {
//    Column {
//        // Título de la sección
//        Text(
//            text = sectionTitle,
//            style = MaterialTheme.typography.headlineSmall,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            textAlign = TextAlign.Center
//        )
//
//        // Fila de libros (solo portadas)
//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(books) { book ->
//                BookCover(
//                    book = book,
//                    onClick = { onBookClick(book.id) }
//                )
//            }
//        }
//
//        // Separador de estantería
//        BookShelfSeparator()
//    }
//}

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
            contentDescription = "book image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
//    Card(
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() }
//    ) {
//        Image(
//            painter = rememberAsyncImagePainter(
//                model = book.imageLinks?.thumbnail?.replace("http", "https") ?: ""
//            ),
//            contentDescription = "Libro: ${book.title}",
//            modifier = Modifier
//                .size(98.dp, 145.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(MaterialTheme.colorScheme.inversePrimary),
//            contentScale = ContentScale.Crop
//        )
//    }
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
            contentDescription = "Separador de estantería",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = 0.3f),
                blendMode = BlendMode.Darken
            )
        )
    }
}
