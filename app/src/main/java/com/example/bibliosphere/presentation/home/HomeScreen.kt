package com.example.bibliosphere.presentation.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliosphere.R
import com.example.bibliosphere.core.navigation.BookDetail
import com.example.bibliosphere.presentation.components.textField.*
import com.example.bibliosphere.presentation.firebase.BookStatusFS
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Composable
fun HomeScreen(navController: NavController,viewModel: HomeScreenViewModel = HomeScreenViewModel()){
    val bookStatus by viewModel.bookStatusFS.observeAsState()
    val topFiveBooks by viewModel.topFiveBooks.observeAsState(emptyList())
    val randomBooks by viewModel.randomBooks.observeAsState(emptyList())

    LaunchedEffect(Unit){
        viewModel.getStatusBooks()
        viewModel.getTopFiveBooks("subject:fantasy")
        viewModel.getRandomBooks()
    }

    ScreenContent(
        bookStatusFS = bookStatus,
        topFiveBooks = topFiveBooks,
        navController = navController,
        onClickBook = { bookId -> navController.navigate(BookDetail.bookRoute(bookId))
        },
        randomBooks = randomBooks
    )

}

@Composable
fun ScreenContent(
    bookStatusFS: List<BookStatusFS>?,
    topFiveBooks: List<Map<String, Any>>,
    navController: NavController,
    onClickBook: (String) -> Unit,
    randomBooks: List<Map<String, Any>>
) {
    val nItems = 4
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = BiblioSphereTheme.dimens.paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val userName = FirebaseAuth.getInstance().currentUser?.displayName
        val readedStatus = bookStatusFS?.count { it.status.contains("LEIDO") } ?: 0
        val readingStatus = bookStatusFS?.count { it.status.contains("LEYENDO") } ?: 0
        val favoriteStatus = bookStatusFS?.count { it.status.contains("FAVORITO") } ?: 0
        val pendingStatus = bookStatusFS?.count { it.status.contains("PENDIENTE") } ?: 0
        val totalBooks = readingStatus + readedStatus + favoriteStatus + pendingStatus

        items(nItems) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = BiblioSphereTheme.dimens.paddingMedium)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeExtraLarge))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(BiblioSphereTheme.dimens.paddingMedium)
            ) {
                when (index) {
                    0 -> { //metemos info en el cuadro 1
                        Welcome(userName)
                    }

                    1 -> {
                        Stats(totalBooks, readedStatus, readingStatus, pendingStatus, favoriteStatus)
                    }

                    2 -> {
                        TopFive(topFiveBooks, onClickBook)
                    }

                    3 -> {
                        RandomBooks(randomBooks, onClickBook)
                    }
                }
            }
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
        }
    }
}

@Composable
private fun RandomBooks(randomBooks: List<Map<String, Any>>, onClickBook: (String) -> Unit) {
    BoxRandomBooks(randomBooks, onClickBook)
}

@Composable
private fun TopFive(topFiveBooks: List<Map<String, Any>>, onClickBook: (String) -> Unit) {

    BoxTopFive(topFiveBooks, onClickBook)
}

@Composable
private fun Stats(
    totalBooks: Int,
    readedStatus: Int,
    readingStatus: Int,
    pendingStatus: Int,
    favoriteStatus: Int
) {
    Column(
        modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
    ) {
        TextTitleStats(
            text = stringResource(R.string.library_stats).uppercase(Locale.ROOT),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
        TextTitleStats(
            text = stringResource(R.string.books_status, totalBooks),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))
        TextStats(
            text = stringResource(R.string.books_read, readedStatus),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerSmall))
        TextStats(
            text = stringResource(R.string.books_reading, readingStatus),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerSmall))
        TextStats(
            text = stringResource(R.string.books_pending, pendingStatus),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerSmall))
        TextStats(
            text = stringResource(R.string.books_favorites, favoriteStatus),
        )
    }
}

@Composable
private fun Welcome(userName: String?) {
    Column(
        modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
    ) {
        Text(
            text = stringResource(R.string.welcome, userName ?: "").uppercase(Locale.ROOT),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomeScreen() {
//    ScreenContent(/*paddingValues = PaddingValues(16.dp)*/)
//}
