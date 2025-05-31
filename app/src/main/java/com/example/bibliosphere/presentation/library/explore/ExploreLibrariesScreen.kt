package com.example.bibliosphere.presentation.library.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bibliosphere.presentation.firebase.UserData
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.bibliosphere.core.navigation.UserLibrary
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import java.util.*

@Composable
fun ExploreLibrariesScreen(viewModel: ExploreLibrariesScreenViewModel, navController: NavController) {

    val users = viewModel.userData.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getUserData()
    }

    if(isLoading.value){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else{
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerNormal),
            verticalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerNormal),
            contentPadding = PaddingValues(BiblioSphereTheme.dimens.paddingMedium)
        ){
            items(users.value){ user ->
                LibraryCard(
                    userData = user,
                    onClick = { navController.navigate(UserLibrary.libraryRoute(user.userId)) },
                )
            }
        }
    }
}

@Composable
fun LibraryCard(
    userData: UserData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp)
            .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeNormal))
            .clickable { onClick() }
            .shadow(120.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = BiblioSphereTheme.dimens.cardElevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        )
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberAsyncImagePainter(userData.profileImage),
                    contentDescription = stringResource(R.string.profile_picture_of, userData.userName),
                    modifier = Modifier.size(84.dp).clip(RoundedCornerShape(42.dp))
                )
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
                Text(text = userData.userName, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
                Text(text = stringResource(R.string.user_books, userData.booksNumber).uppercase(Locale.ROOT), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
