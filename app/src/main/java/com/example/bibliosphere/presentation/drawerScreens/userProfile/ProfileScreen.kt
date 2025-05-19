package com.example.bibliosphere.presentation.drawerScreens.userProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.presentation.components.PrimaryButton
import com.example.bibliosphere.presentation.components.ProfileImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = remember{ProfileScreenViewModel()}) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {mutableStateOf(false)}
    val imageResId by viewModel.imageResId.collectAsState()

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier
            .width(IntrinsicSize.Min)
            .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //imagen

            Card(
                modifier = Modifier
                    .size(140.dp)
                    .clickable { showBottomSheet = true },
                shape = CircleShape,

            ){
                val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageResId)
                        .size(252)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            //contenido

            PrimaryButton(
                text = "Editar perfil",
                onClick = { /* activar modo edición */},
                modifier = Modifier.fillMaxWidth(),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor   = MaterialTheme.colorScheme.onBackground
                ),
            )
        }


    }

    //para mostrar/ocultar hoja inferior
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
           Text(text = "Select your image profile",
               style = MaterialTheme.typography.bodyMedium,
               modifier = Modifier.padding(16.dp)
           )
            ProfileImage{ pickedResId ->
                viewModel.setImageResId(pickedResId)
                showBottomSheet = false
            }
        }
    }

}

@Composable
fun UserDataProfile(
    userName: String,
    email: String,
    birthDate: String,
    enableNotifications: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Datos de usuario",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            //nombre usuario
            Row(
                modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}