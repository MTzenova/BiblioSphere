package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge,
) {
    var timeTextStyle by remember { mutableStateOf(textStyle) }
    val fontSizeFactor = 0.95
    Text(
        text,
        modifier = modifier.fillMaxWidth(), //por ahora lo dejamos asi, pero no es recomendable
        softWrap = false, //para pantalla pequeña, que no se rompa ni salte, que se quede en la misma línea
        style = timeTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                timeTextStyle = textStyle.copy(
                    fontSize = timeTextStyle.fontSize * fontSizeFactor //para que se vaya haciendo un poco mñas pequeño hasta que cumpla la condición
                )
            }
        }
    )
}

//Preview annotation
@Preview(
    name = "AutoResizedTextPreview",
    showBackground = true,
)
//Composable para preview
@Composable
fun AutoResizedTextPreview(){
    //Tema
    BiblioSphereTheme{
        //Composable to preview
        AutoResizedText(
            text = "Hello World!",
        )

    }
}