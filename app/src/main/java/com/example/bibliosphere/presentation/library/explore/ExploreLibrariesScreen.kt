package com.example.bibliosphere.presentation.library.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.example.bibliosphere.core.navigation.UserLibrary

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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
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
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .shadow(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberAsyncImagePainter(userData.profileImage),
                    contentDescription = "${userData.userName} profile picture",
                    modifier = Modifier.size(84.dp).clip(RoundedCornerShape(42.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = userData.userName)
                Text(text = "Libros: ${userData.booksNumber}")
            }
        }
    }
}
