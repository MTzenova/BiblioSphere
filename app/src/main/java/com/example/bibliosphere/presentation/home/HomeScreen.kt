package com.example.bibliosphere.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.AuthState
import com.example.bibliosphere.presentation.AuthViewModel
import com.example.bibliosphere.presentation.components.AutoResizedText
import com.example.bibliosphere.presentation.components.CustomButton
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = HomeScreenViewModel(), authViewModel: AuthViewModel,
               navigateToLogin: () -> Unit){
    //pruebas de signout
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navigateToLogin()
            else -> Unit
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(BiblioSphereTheme.dimens.paddingMedium)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ){
            Icon(//esto lo podemos usar para el menú lateral
                modifier = Modifier.size(BiblioSphereTheme.dimens.iconSizeNormal),
                painter = painterResource(id = R.drawable.logo_bibliosphere), //cambiar esto por icono de menú
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        AutoResizedText(
            text = "BiblioSphere",
            textStyle = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            ),
        )
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium)) //espacio entre elementos
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            CustomButton(
                text = "Cerrar sesión",
                textColor = MaterialTheme.colorScheme.surface,
                buttonColor = MaterialTheme.colorScheme.primary,
                onTap = {
                    authViewModel.signout()
                }
            )
        }
    }
}
