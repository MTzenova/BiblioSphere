package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R

@Composable
fun ProfileImage(
    onPicked: (Int) -> Unit = {}
) {

    val imageList = listOf(
        R.drawable.profile_alien,
        R.drawable.profile_astronaut,
        R.drawable.profile_chilli,
        R.drawable.profile_dino,
        R.drawable.profile_dino2,
        R.drawable.profile_koala1,
        R.drawable.profile_koala2,
        R.drawable.profile_lemon,
        R.drawable.profile_mosnter,
        R.drawable.profile_yeti
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(imageList) { resId ->
            ImageSelector(
                resId = resId,
                onClick = { onPicked(resId) }
            )
        }
    }

}

@Composable
fun ImageSelector(resId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(84.dp)
            .clickable(onClick = onClick)
    ) {

        val context = LocalContext.current
        val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
            ImageRequest.Builder(context)
                .data(resId)
                .size(252)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileImagePreview() {
    ProfileImage()
}