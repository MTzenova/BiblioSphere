package com.example.bibliosphere.presentation.library.myLibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode

import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun MyLibraryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Para la textura del fondo
        val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
            ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.wood_texture)
                .size(252)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "Textura de la estantería",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            //aqui poner los libros
            Text(
                text = "Texto sobre madera",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
            BookShelfSeparator()

        }

    }
}

@Preview
@Composable
fun MyLibraryScreenPreview() {
    MyLibraryScreen()
}

@Composable
fun BookShelfSeparator(){
    Surface(
        shadowElevation = 18.dp,
        tonalElevation = 50.dp,
        modifier = Modifier.fillMaxWidth().height(20.dp).border(
            width = 2.dp,
            color = Color.Black,
        ),
        shape = RectangleShape,


    ) {
        val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
            ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.wood_texture)
                .size(252)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "Separador",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.3f),
                blendMode = BlendMode.Darken)
        )
    }
}

@Composable
fun BookColumn(){

}