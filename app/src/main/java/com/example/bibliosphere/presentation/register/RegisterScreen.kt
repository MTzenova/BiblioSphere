package com.example.bibliosphere.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier

@Composable
fun RegisterScreen(viewModel: RegisterScreenViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
    ){
        Register(Modifier.align(Alignment.Center), viewModel)
    }

}

@Composable
fun Register(modifier: Modifier, viewModel: RegisterScreenViewModel) {

}