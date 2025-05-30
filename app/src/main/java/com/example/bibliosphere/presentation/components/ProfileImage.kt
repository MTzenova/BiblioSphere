package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
        R.drawable.logo_sin_letras,
        R.drawable.profile_alien,
        R.drawable.profile_astronaut,
        R.drawable.profile_chilli,
        R.drawable.profile_dino,
        R.drawable.profile_dino2,
        R.drawable.profile_koala1,
        R.drawable.profile_koala2,
        R.drawable.profile_lemon,
        R.drawable.profile_mosnter,
        R.drawable.profile_yeti,
        R.drawable.profile_astro_robot,
        R.drawable.profile_burguer_cat,
        R.drawable.profile_cat_eating,
        R.drawable.profile_cat_sunglases,
        R.drawable.profile_cow_superhero,
        R.drawable.profile_dino_blue,
        R.drawable.profile_donut_cat,
        R.drawable.profile_fox_sunglases,
        R.drawable.profile_orange_cat,
        R.drawable.profile_penguin_balloons,
        R.drawable.profile_baby_dragon_fire,
        R.drawable.profile_baby_dragon_red,
        R.drawable.profile_brontosaurus_blue,
        R.drawable.profile_cute_astronaut_alien,
        R.drawable.profile_cute_panda,
        R.drawable.profile_elephant_gamer,
        R.drawable.profile_halloween_cat,
        R.drawable.profile_mouse_heart,
        R.drawable.profile_panda_detective,
        R.drawable.profile_tyranosaur_red,
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),


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
            .size(100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
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