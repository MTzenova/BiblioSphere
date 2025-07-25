package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bibliosphere.presentation.components.RatingBooks
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import com.example.bibliosphere.R
import java.util.*

@Composable
fun TextStats(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun TextTitleStats(text:String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun BoxTopFive(topFiveBooks: List<Map<String, Any>>, onClickBook: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
    ) {
        TextTitleStats(
            text = stringResource(R.string.top_five_books).uppercase(Locale.ROOT),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingSmall))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerMedium),
            verticalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerMedium),
            modifier = Modifier.height(180.dp)
        ) {
            items(topFiveBooks) { item ->
                val coverBook = (item["thumbnail"] as? String)?.replace("http://", "https://") ?: ""
                val ratingBook = ((item["rating"] as? Double) ?: 0f).toFloat()
                val id = item["id"] as? String
                Column(
                    modifier = Modifier
                        .padding(BiblioSphereTheme.dimens.paddingNormal)
                        .clickable {
                            if (id != null) {
                                onClickBook(id)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = coverBook),
                        contentDescription = stringResource(R.string.book_cover),
                        modifier = Modifier
                            .size(98.dp, 145.dp)
                            .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeNormal))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(BiblioSphereTheme.dimens.paddingNormal),


                    )
                    Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerSmall))
                    RatingBooks(ratingBook, 5)
                }

            }
        }
    }
}

@Composable
fun BoxRandomBooks(randomBooks: List<Map<String, Any>>, onClickBook: (String) -> Unit){
    Column(
        modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
    ){

        TextTitleStats(
            text = stringResource(R.string.recommendations).uppercase(Locale.ROOT),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingSmall))
        TextStats(
            text = stringResource(R.string.random_books),
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerMedium),
            verticalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerMedium),
            modifier = Modifier.height(180.dp)
        ){
            items(randomBooks) { itemRandom ->
                val coverBook = (itemRandom["thumbnail"] as? String)?.replace("http://", "https://") ?: ""
                val id = itemRandom["id"] as? String
                val ratingBook = ((itemRandom["rating"] as? Double) ?: 0f).toFloat()

                Column(
                    modifier = Modifier
                        .padding(BiblioSphereTheme.dimens.paddingNormal)
                        .clickable {
                            if (id != null) {
                                onClickBook(id)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = coverBook),
                        contentDescription = stringResource(R.string.book_cover),
                        modifier = Modifier
                            .size(98.dp, 145.dp)
                            .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeNormal))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(BiblioSphereTheme.dimens.paddingNormal)
                    )
                    Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingSmall))
                    RatingBooks(ratingBook, 5)
                }

            }
        }
    }
}