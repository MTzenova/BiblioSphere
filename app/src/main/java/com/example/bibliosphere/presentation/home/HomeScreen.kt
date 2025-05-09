package com.example.bibliosphere.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = HomeScreenViewModel(),
    paddingValues: PaddingValues
){

    ScreenContent(
        paddingValues = paddingValues,
    )

}

@Composable
fun ScreenContent(paddingValues: PaddingValues){
    val nItems = 4
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            top = paddingValues.calculateBottomPadding() + 16.dp
        )
    ){
        items(nItems) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp) // padding INTERNO del contenido,
            ){
                when (index) {
                    0 -> { //metemos info en el cuadro 1
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "¡Bienvenido user!",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "BIBLIOTECA - ESTADÍSTICAS",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Estado actual de tus libros:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Leídos: Has leído X libros en total.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "En proceso: Tienes X libros sin terminar.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Próximos: Tienes X libros por empezar.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    2 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "TOP 5 LIBROS CON MAYOR PUNTUACIÓN",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    3 -> {
                        Column(
                            modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingNormal)
                        ){
                            Text(
                                text = "RECOMENDACIONES",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "¿No sabes qué leer? Aquí tienes algunos libros random:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    ScreenContent(paddingValues = PaddingValues(16.dp))
}
