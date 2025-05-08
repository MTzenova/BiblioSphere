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
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = HomeScreenViewModel(), authViewModel: AuthViewModel,
    navigateToLogin: () -> Unit,
    paddingValues: PaddingValues
){
    //pruebas de signout
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navigateToLogin()
            else -> Unit
        }
    }

    ScreenContent(
        paddingValues = paddingValues,
    )

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(BiblioSphereTheme.dimens.paddingMedium)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Box(
//            modifier = Modifier.fillMaxWidth(),
//            contentAlignment = Alignment.TopEnd
//        ){
//            Icon(//esto lo podemos usar para el menú lateral
//                modifier = Modifier.size(BiblioSphereTheme.dimens.iconSizeNormal),
//                painter = painterResource(id = R.drawable.logo_bibliosphere), //cambiar esto por icono de menú
//                contentDescription = "Menu",
//                tint = MaterialTheme.colorScheme.primary,
//            )
//        }
//        AutoResizedText(
//            text = "BiblioSphere",
//            textStyle = MaterialTheme.typography.displayMedium.copy(
//                color = MaterialTheme.colorScheme.primary,
//                textAlign = TextAlign.Center
//            ),
//        )
//        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium)) //espacio entre elementos
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//        ){
//            CustomButton(
//                text = "Cerrar sesión",
//                textColor = MaterialTheme.colorScheme.surface,
//                buttonColor = MaterialTheme.colorScheme.primary,
//                onTap = {
//                    authViewModel.signout()
//                }
//            )
//        }
//    }
}

@Composable
fun ScreenContent(paddingValues: PaddingValues){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding()
        )
    ){
        items(10) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary),
            )
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingMedium))
        }
    }
}

