package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun BookDetailCard(
    author: String,
    title: String,
    image: String,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.TopCenter
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            //imagen
            Image(
                painter = rememberAsyncImagePainter(model = image.replace("http", "https")),
                contentDescription = "Book of $author",
                modifier = Modifier
                    .size(width = 140.dp, height = 200.dp)
                    .offset(y = (-30).dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))
            //contenido
            Text(
                text = author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChipView()
        }


    }
}

@Composable
fun BookDescription(
    description: String
){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailCardPreview() {
    BookDetailCard(
        "J.K. Rowlings",
        "Harry Potter y el cáliz de fuego",
        "https://upload.wikimedia.org/wikipedia/en/a/a9/Harry_Potter_and_the_Goblet_of_Fire.jpg"
    )
}