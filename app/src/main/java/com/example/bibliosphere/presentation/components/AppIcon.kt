package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    lightLogo: Int = R.drawable.logo_bibliosphere,
    darkLogo: Int = R.drawable.logo_biblioshpere_oscuro,
    width: Dp,
    contentDescription: String = "Logo"
){
    val isDarkTheme = isSystemInDarkTheme()
    //si está en modo oscuro o claro
    val logo = if(isDarkTheme){
        R.drawable.logo_biblioshpere_oscuro //logo para modo osucor
    }else{
        R.drawable.logo_bibliosphere //logo para modo claro
    }

    Row(Modifier
        .fillMaxWidth()
        .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(200.dp),
            painter = painterResource(id= logo),
            contentDescription = "Imagen login",
        )
    }

}