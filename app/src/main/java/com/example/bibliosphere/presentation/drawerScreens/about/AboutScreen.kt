package com.example.bibliosphere.presentation.drawerScreens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("¿Qué necesitas saber sobre nosotros?")
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Somos lectores, al igual que vosotros, usuarios.\n" +
                        "\n" +
                        "Esta aplicación es por y para vosotros." +
                        "\n" +
                        "Todas las búsquedas de libros se realizan a través de Google Libros.\n" +
                        "\n" +
                        "En BiblioSphere, estamos encantados de que estéis con nosotros compartiendo libros y la pasión sobre la lectura, comentando opiniones, haciendo recomendaciones y sobre todo, formando parte de esta bonita comunidad.\n" +
                        "\n" +
                        "Contacta con BiblioSphere a través del correo electrónico.")
            }
        }
    }
}


@Preview(showBackground = true,showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}