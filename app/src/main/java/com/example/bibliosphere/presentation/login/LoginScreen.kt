package com.example.bibliosphere.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun LoginScreen() {
    //definir variables
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }


    //código visual
    Box(Modifier.fillMaxSize().padding(BiblioSphereTheme.dimens.paddingMedium).background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.align(Alignment.Center).padding(BiblioSphereTheme.dimens.paddingMedium).fillMaxWidth()){
            Card(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium),
                shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeNormal),
                //elevation = BiblioSphereTheme.dimens.spacerNormal
            ) {

            }
        }
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

    )
}