package com.example.bibliosphere.presentation.drawerScreens.userProfile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
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
import com.example.bibliosphere.presentation.components.TextFieldDataUser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = remember{ProfileScreenViewModel()}) {

    val sheetState = rememberModalBottomSheetState()
    val imageResId by viewModel.imageResId.collectAsState()
    val scrollState = rememberScrollState()

    var showBottomSheet by remember {mutableStateOf(false)}
    var showButtons by remember {mutableStateOf(false)}
    var editable by remember {mutableStateOf(false)}
    var profileEditable by remember {mutableStateOf(false)}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
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
                        .clickable(enabled = editable) { showBottomSheet = true }, //solo se puede ahcer click si es true
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
                //botón editar

                PrimaryButton(
                    text = "Editar perfil",
                    onClick = { profileEditable = true }, //activar modo edición
                    modifier = Modifier.fillMaxWidth(),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor   = MaterialTheme.colorScheme.onBackground
                    ),
                )

            }

        }
        Column( modifier = Modifier.align(Alignment.Start).padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp)) {
            Text("Datos del perfil:")
        }
        Box( //caja para los datos del perfil
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.TopStart,
        ){
            UserDataProfile(
                userName = "Shayleen",
                email = "monica_97_ct@hotmail.com",
                password = "*********",
                birthDate = "18/03/1997",
                enableNotifications = true,
                editable = editable,
            )
        }
        if(profileEditable){
            editable = true //activamos modo edición. Los text field ahora ya no están solo en modo lectura
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = "Guardar",
                    onClick = { /* guardar en firebase */ profileEditable = false},
                    modifier = Modifier.weight(1f),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                )

                PrimaryButton(
                    text = "Cancelar",
                    onClick = { /* cancelar y desactivar modo edicion */ profileEditable = false },
                    modifier = Modifier.weight(1f),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                )
            }
        }
        else{
            editable = false
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
    password: String,
    enableNotifications: Boolean,
    modifier: Modifier = Modifier,
    editable: Boolean,
) {

    var name by remember { mutableStateOf(userName) }
    var mail by remember { mutableStateOf(email) }
    var birth by remember { mutableStateOf(birthDate) }
    var password by remember { mutableStateOf(password)}


    //cargar de firebase
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextFieldDataUser(
            value = name,
            onValueChange = { name = it },
            label = "Nombre de usuario",
            leadingIcon = Icons.Filled.Person,
            modifier = Modifier.fillMaxWidth(),
            editable = editable,
        )

        TextFieldDataUser(
            value = mail,
            onValueChange = { mail = it },
            label = "Correo electrónico",
            leadingIcon = Icons.Filled.Email,
            modifier = Modifier.fillMaxWidth(),
            editable = editable,
        )

        TextFieldDataUser(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            leadingIcon = Icons.Filled.Key,
            modifier = Modifier.fillMaxWidth(),
            editable = editable,
        )

        TextFieldDataUser(
            value = birth,
            onValueChange = { birth = it },
            label = "Fecha de nacimiento",
            leadingIcon = Icons.Filled.DateRange,
            modifier = Modifier.fillMaxWidth(),
            editable = editable,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    UserDataProfile("Shayleen","monica_97_ct@hotmail.com","**********","18/03/1997", true, editable = true )
}