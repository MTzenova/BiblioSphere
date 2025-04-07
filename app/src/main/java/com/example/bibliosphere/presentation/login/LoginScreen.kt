package com.example.bibliosphere.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun LoginScreen() {
    Box(Modifier.fillMaxSize().padding(BiblioSphereTheme.dimens.paddingMedium)) {
        Login(Modifier.align(Alignment.Center))
    }
}
@Composable
fun Login(modifier: Modifier) {
    Column(modifier = modifier) {
        //HeaderImage()
    }
}


@Composable
fun HeaderImage() {
    Image(painter= painterResource(id = R.drawable.logo_bibliosphere), contentDescription = "Header")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmailField() {
    TextField(value = "", onValueChange = {},
        Modifier.fillMaxWidth(),
        placeholder = {Text("email@example.com")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true, //para que no se aplie el componente
        maxLines = 1,
        //colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.Bib)
    )
}