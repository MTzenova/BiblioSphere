package com.example.bibliosphere.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.firebase.BookStatusFS
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = HomeScreenViewModel(),
){
    val bookStatus by viewModel.bookStatusFS.observeAsState()

    LaunchedEffect(Unit){
        viewModel.getStatusBooks()
    }

    ScreenContent(bookStatusFS = bookStatus)

}

@Composable
fun ScreenContent(bookStatusFS: List<BookStatusFS>?){
    val nItems = 4
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = PaddingValues(
//            top = paddingValues.calculateBottomPadding()
//        )
    ){

        bookStatusFS?.forEach {
            Log.d("DEBUG", "BookId: ${it.bookId} - Status: '${it.status}'")
        }

        val userName = FirebaseAuth.getInstance().currentUser?.displayName
        val readedStatus = bookStatusFS?.count { it.status.contains("LEIDO") } ?: 0
        val readingStatus = bookStatusFS?.count { it.status.contains("LEYENDO") }?: 0
        val favoriteStatus = bookStatusFS?.count { it.status.contains("FAVORITO") }?: 0
        val pendingStatus = bookStatusFS?.count { it.status.contains("PENDIENTE")  }?: 0
        val totalBooks = readingStatus + readedStatus + favoriteStatus + pendingStatus


        items(nItems) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp)
            ){
                when (index) {
                    0 -> { //metemos info en el cuadro 1
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "¡Bienvenido $userName!",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "BIBLIOTECA - ESTADÍSTICAS",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Estado actual de tus $totalBooks libros:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Leídos: Tienes $readedStatus libros.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Leyendo: Tienes $readingStatus libros.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Pendientes: Tienes $pendingStatus libros.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Favoritos: Tienes $favoriteStatus libros.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    2 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "TOP 5 LIBROS CON MAYOR PUNTUACIÓN",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    3 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "RECOMENDACIONES",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "¿No sabes qué leer? Aquí tienes algunos libros random:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomeScreen() {
//    ScreenContent(/*paddingValues = PaddingValues(16.dp)*/)
//}
