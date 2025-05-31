package com.example.bibliosphere.presentation.drawerScreens.userProfile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.components.textField.DatePickerFieldToModal
import com.example.bibliosphere.presentation.components.ProfileImage
import com.example.bibliosphere.presentation.components.buttons.ProfileButton
import com.example.bibliosphere.presentation.components.textField.TextFieldDataUser
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = remember{ProfileScreenViewModel()}) {

    val userName by viewModel.userName.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    val birthDate by viewModel.userBirthday.collectAsState()
    val imageResId by viewModel.imageResId.collectAsState()

    var name by remember { mutableStateOf(userName) }
    var mail by remember { mutableStateOf(email) }
    var birth by remember { mutableStateOf(birthDate) }

    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()

    var showBottomSheet by remember {mutableStateOf(false)}
    //var showButtons by remember {mutableStateOf(false)}
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
                .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeLarge))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(top = 20.dp, bottom = 20.dp, start = BiblioSphereTheme.dimens.paddingMedium, end = BiblioSphereTheme.dimens.paddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //imagen

                Card(
                    modifier = Modifier
                        .size(140.dp)
                        .clickable(enabled = editable) { showBottomSheet = true }, //solo se puede ahcer click si es true
                    shape = CircleShape,
                    border = if (editable) BorderStroke(5.dp, MaterialTheme.colorScheme.background) else null,
                    elevation = CardDefaults.cardElevation(defaultElevation = BiblioSphereTheme.dimens.cardElevation),
                    ){
                    val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
                        ImageRequest.Builder(LocalContext.current)
                            .data(imageResId)
                            .size(252)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = stringResource(R.string.profile_picture),
                        modifier = Modifier.fillMaxSize(),

                    )
                }

                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.paddingNormal))
                //botón editar

                ProfileButton(
                    text = stringResource(R.string.edit_profile),
                    onClick = {
                        profileEditable = true
                        name = userName
                        mail = email
                        birth = birthDate
                    }, //activar modo edición
                    modifier = Modifier.fillMaxWidth(),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                )

            }

        }
        Column( modifier = Modifier
            .align(Alignment.Start)
            .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp)) {
            Text(stringResource(R.string.profile_data), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Box( //caja para los datos del perfil
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeLarge))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.TopStart,
        ){

            UserDataProfile(
                userName = userName,
                name = name,
                email = email,
                birthDate = birthDate,
                birth = birth,
                editable = editable,
                onNameChange = { name = it },
                onBirthDateChange = { birth = it },
            )
        }
        if(profileEditable){
            editable = true //activamos modo edición. Los text field ahora ya no están solo en modo lectura
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = BiblioSphereTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.spacedBy(BiblioSphereTheme.dimens.spacerNormal),
            ) {
                ProfileButton(
                    text = stringResource(R.string.save),
                    onClick = {
                        viewModel.updateImageResId(imageResId)
                        viewModel.updateProfileData(userName = name, userBirthday = birth)
                        profileEditable = false
                    },
                    modifier = Modifier.weight(1f),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                )

                ProfileButton(
                    text = stringResource(R.string.cancel),
                    onClick = {
                        profileEditable = false
                        name = userName
                        mail = email
                        birth = birthDate
                    },
                    /* cancelar y desactivar modo edicion */
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
           Text(text = stringResource(R.string.select_image),
               style = MaterialTheme.typography.bodyMedium,
               modifier = Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)
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
    name:String,
    email: String,
    birthDate: String,
    birth:String,
    modifier: Modifier = Modifier,
    editable: Boolean,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
) {
    //cargar de firebase
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextFieldDataUser(
            value = if(editable) name else userName,
            onValueChange = onNameChange,
            label = stringResource(R.string.user_name),
            leadingIcon = Icons.Filled.Person,
            modifier = Modifier.fillMaxWidth(),
            editable = editable,
        )

        DatePickerFieldToModal(
            birthDate = if(editable) birth else birthDate,
            onBirthDateChange = onBirthDateChange,
            isValidDate = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = editable,
            isProfile = true,
            leadingIcon = Icons.Filled.DateRange,
        )

        TextFieldDataUser(
            value = email,
            onValueChange =  {},
            label = stringResource(R.string.email),
            leadingIcon = Icons.Filled.Email,
            modifier = Modifier.fillMaxWidth(),
            editable = false,
        )

    }
}
