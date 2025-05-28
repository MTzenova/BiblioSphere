package com.example.bibliosphere.presentation.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun TextStats(text:String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun BoxTopFive(topFiveBooks: List<Map<String, Any>>, onClickBook: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
    ) {

        Text(
            text = "TOP 5 LIBROS CON MAYOR PUNTUACIÓN",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(180.dp)
        ) {
            items(topFiveBooks) { item ->
                val coverBook = (item["thumbnail"] as? String)?.replace("http://", "https://") ?: ""
                val ratingBook = ((item["rating"] as? Double) ?: 0f).toFloat()
                val id = item["id"] as? String
                Column(
                    modifier = Modifier
                        .padding(BiblioSphereTheme.dimens.paddingNormal)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            if (id != null) {
                                onClickBook(id)
                            }
                        },
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = coverBook),
                        contentDescription = "Book cover",
                        modifier = Modifier
                            .size(98.dp, 145.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .padding(8.dp)


                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    RatingBooks(ratingBook, 5)
                }

            }
        }
    }
}