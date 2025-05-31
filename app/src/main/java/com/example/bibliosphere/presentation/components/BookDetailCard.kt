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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.components.buttons.BookStateButtons
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun BookDetailCard(
    author: String,
    title: String,
    image: String,
    type: String,
    averageRating: Float?,

    initialStates: Set<BookState> = emptySet(),
    onStatesChanged: (Set<BookState>) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 15.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.TopCenter
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = BiblioSphereTheme.dimens.paddingMedium, end = BiblioSphereTheme.dimens.paddingMedium,),
                horizontalAlignment = Alignment.CenterHorizontally) {

                //imagen
                Image(
                    painter = rememberAsyncImagePainter(model = image.replace("http", "https")),
                    contentDescription = stringResource(id = R.string.book_image_from,author),
                    modifier = Modifier
                        .size(width = 140.dp, height = 200.dp)
                    //.offset(y = (-30).dp)
                    //.clip(RoundedCornerShape(20.dp))
                )
                RatingBooks(averageRating = averageRating)
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingNormal))
                //contenido
                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingNormal))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingNormal))
                ChipView(type = type)
            }
        }
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp, start =  BiblioSphereTheme.dimens.paddingMedium, end = BiblioSphereTheme.dimens.paddingMedium)
                .fillMaxWidth()
                .clip(RoundedCornerShape( BiblioSphereTheme.dimens.paddingMedium))
                .background(MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //añadir botones
            BookStateButtons(initialStates, onStatesChanged)
        }
    }

}

@Composable
fun BookDescription(description: String) {
//    AndroidView(
//        factory = { context ->
//            TextView(context).apply {
//                text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
//            }
//        },
//        modifier = Modifier.padding(16.dp),
//    )
    ExpandingText(
        description = description,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Start
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailCardPreview() {
    BookDetailCard(
        "J.K. Rowlings",
        "Harry Potter y el cáliz de fuego",
        "https://upload.wikimedia.org/wikipedia/en/a/a9/Harry_Potter_and_the_Goblet_of_Fire.jpg",
        type = "Fantasia",
        averageRating = 2f
    )
}