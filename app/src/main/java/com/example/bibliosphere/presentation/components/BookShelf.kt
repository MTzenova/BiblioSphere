package com.example.bibliosphere.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R
import com.example.bibliosphere.data.model.remote.ImageLinks
import com.example.bibliosphere.presentation.components.buttons.BookState
import com.example.bibliosphere.presentation.firebase.BookData
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

data class BookFromFirestore(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val imageLinks: ImageLinks? = null,
    val status: String = ""
)

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
            contentDescription = stringResource(R.string.book_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun BookStatusFilter(
    statusSelected:String?,
    onSatusSelected: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val statusList = BookState.entries

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeMedium))
            .animateContentSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))

    ){
        Row(modifier = Modifier
            .fillMaxWidth().padding(BiblioSphereTheme.dimens.paddingNormal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ){
            Text(text = stringResource(R.string.filter_status))

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription =
                        if(expanded){
                            stringResource(R.string.show_less)
                        }else{
                            stringResource(R.string.show_more)
                        }
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            //quita el status seleciconado
            Text(text = stringResource(R.string.clear_filters),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable {
                    onSatusSelected(null)
                    expanded = false
                },
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline,

            )
        }
        if (expanded) {
            FlowRow(modifier = Modifier
                .padding(BiblioSphereTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                statusList.forEach { bookStatus ->
                    AssistChip(
                        onClick = {
                            onSatusSelected(bookStatus.name)
                            expanded = false
                        },
                        label ={ Text(text = bookStatus.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        colors = if (bookStatus.name == statusSelected){
                            AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        }else{
                            AssistChipDefaults.assistChipColors()
                        }
                    )
                }
            }
        }
    }

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
            contentDescription = stringResource(R.string.bookshelf_separator),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = 0.3f),
                blendMode = BlendMode.Darken
            )
        )
    }
}
