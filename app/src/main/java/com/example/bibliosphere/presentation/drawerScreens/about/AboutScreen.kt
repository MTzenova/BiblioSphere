package com.example.bibliosphere.presentation.drawerScreens.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun AboutScreen() {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
    ) {

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("¿Qué necesitas saber sobre nosotros?", textAlign = TextAlign.Start)
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            ) {
                Text(
                    text = ("Somos lectores, al igual que vosotros, usuarios.\n" +
                            "\n" +
                            "Esta aplicación es por y para vosotros." +
                            "\n" +
                            "Todas las búsquedas de libros se realizan a través de Google Libros.\n" +
                            "\n" +
                            "En BiblioSphere, estamos encantados de que estéis con nosotros compartiendo libros y la pasión sobre la lectura, comentando opiniones, haciendo recomendaciones y sobre todo, formando parte de esta bonita comunidad."),
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Contacta con BiblioSphere a través de: ", textAlign = TextAlign.Start)
                Text(
                    text = ("Correo electrónico."),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textDecoration = TextDecoration.Underline
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:bibliosphereapp@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Support")
                        }
                        startActivity(context, intent, null)
                    }

                )

            }
        }
    }
}


@Composable
fun AnnotatedStringWithListenerSample() {
    // Display a link in the text and log metrics whenever user clicks on it. In that case we handle
    // the link using openUri method of the LocalUriHandler
    val uriHandler = LocalUriHandler.current
    Text(
        buildAnnotatedString {
            append("Build better apps faster with ")
            val link =
                LinkAnnotation.Url(
                    "https://developer.android.com/jetpack/compose",
                    TextLinkStyles(SpanStyle(color = Color.Blue))
                ) {
                    val url = (it as LinkAnnotation.Url).url
                    // log some metrics
                    uriHandler.openUri(url)
                }
            withLink(link) { append("Jetpack Compose") }
        }
    )
}


@Preview(showBackground = true,showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}