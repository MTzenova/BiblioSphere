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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            top = paddingValues.calculateBottomPadding() + 16.dp
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    ScreenContent(paddingValues = PaddingValues(16.dp))
}
