package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.components.buttons.BookStateButtons
import com.example.bibliosphere.presentation.theme.primaryBlack
import com.example.bibliosphere.R

@Composable
fun ItemBookList(
    author:String,
    title:String,
    image: ImageLinks,
    onClick:()->Unit,
    type:String,
    initialStates: Set<BookState> = emptySet(),
    onStatesChanged: (Set<BookState>) -> Unit = {}
) {
    Card(modifier = Modifier.padding(16.dp).clickable { onClick()}) {

        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            //imagen
            //aqui podriamos colocar los botones de añadir a la biblioteca
            Image(
                painter = rememberAsyncImagePainter(model = image.thumbnail?.replace("http", "https") ?: ""),
                contentDescription = stringResource(R.string.book_image_from),
                modifier = Modifier.size(98.dp,145.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.inversePrimary).padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            //contenido
            Column {
                Text(text = author, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                ChipView(type = type)

                //añadir botones
                BookStateButtons(initialStates, onStatesChanged)
            }
        }
    }
}

@Composable
fun ChipView(type:String){
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(primaryBlack.copy(alpha = 0.10f))
            .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp),
        contentAlignment = Alignment.Center
    ){
        Text(text = type, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemBookListPreview() {
    ItemBookList(author = "J.K.Rowling", title = "Harry Potter", image = ImageLinks(thumbnail = "https://upload.wikimedia.org/wikipedia/en/a/a9/Harry_Potter_and_the_Goblet_of_Fire.jpg"),onClick = {},type="Fantasy")
}