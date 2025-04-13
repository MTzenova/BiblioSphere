package com.example.bibliosphere.presentation.login

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.graphics.painter.Painter
import com.example.bibliosphere.presentation.components.PrimaryButton
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginScreenViewModel) {

    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onSecondary)) {
        Login(Modifier.align(Alignment.Center), viewModel)
    }


}

@Composable
fun Login(modifier: Modifier, viewModel: LoginScreenViewModel){

    //definir variables
    val email:String by viewModel.email.observeAsState(initial="")
    val isValidEmail by viewModel.isValidEmail.observeAsState(false)
    val password:String by viewModel.password.observeAsState(initial="")
    val passwordVisible by viewModel.passwordVisible.observeAsState(initial=false)
    val isValidPassword by viewModel.isValidPassword.observeAsState(false)
    val loginEnable:Boolean by viewModel.loginEnable.observeAsState(initial = false)

    val isLoading:Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    if(isLoading){
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{

        //código visual
        Column(modifier = modifier) {
            Column(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)) {
                RowImage()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Iniciar sesión",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(start = BiblioSphereTheme.dimens.paddingNormal,
                                top = BiblioSphereTheme.dimens.paddingNormal)
                    )
                }

                RowEmail(
                    email = email,
                    emailChange = { viewModel.onLoginChanged(it, password) },
                    isValidEmail = isValidEmail
                )

                RowPassword(
                    password = password,
                    passwordVisible = passwordVisible,
                    passwordVisibleChange = { viewModel.togglePasswordVisibility() },
                    passwordChange = { viewModel.onLoginChanged(email, it) },
                    isValidPassword = isValidPassword
                )


                RowForgottenPassword{

                    Toast.makeText(context, "Función no implementada aún", Toast.LENGTH_SHORT).show()
                }

                RowButtonLogin(loginEnable){
                    coroutineScope.launch {
                        viewModel.onLoginSelected()
                    }
                }

                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))

                RowLoginWith()

                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))

                RowNoAccount {
                    Toast.makeText(context, "Función no implementada aún", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}

//no cuenta
@Composable
fun RowNoAccount(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "¿Aún no tienes una cuenta? Regístrate",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

//boton para botones de facebook y google
@Composable
fun LoginWithButton(
    icon: Painter,
    text: String,
    textColor: Color,
    onClick: () -> Unit,
    isFacebook: Boolean = false, //para redimensionar el icono de facebook, que se quedaba pequeño
    buttonColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = "$text icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(if(isFacebook)40.dp else 24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor)
    }
}



@Composable
fun RowLoginWith() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            Text(
                text = "O iniciar sesión con",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))

        // Botón Google
        LoginWithButton(
            icon = painterResource(id = R.drawable.google_icon),
            text = "Google",
            textColor = Color.White,
            onClick = { /* TODO: iniciar sesión con Google */ },
            buttonColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))

        // Botón Facebook
        LoginWithButton(
            icon = painterResource(id = R.drawable.facebook_icon),
            text = "Facebook",
            textColor = Color.White,
            onClick = { /* TODO: iniciar sesión con Facebook */ },
            isFacebook = true,
            buttonColor = MaterialTheme.colorScheme.primary
        )
    }
}





//contraseña olvidada
@Composable
fun RowForgottenPassword(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


//boton login
@Composable
fun RowButtonLogin(
    loginEnable: Boolean,
    onLoginSelected: () -> Unit,
    ){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center){
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            onClick = {onLoginSelected()},
//            enabled = loginEnable
//        ){
//            Text(text = "Iniciar Sesión")
//        }
        PrimaryButton("Iniciar sesión", onClick = onLoginSelected, enabled = loginEnable)
    }

}

//campo password
@Composable
fun RowPassword(
    password: String,
    passwordChange:(String)->Unit,
    passwordVisible:Boolean,
    passwordVisibleChange:()->Unit,
    isValidPassword:Boolean
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = password,
            onValueChange = { passwordChange(it) },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Contraseña")},
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(
                    onClick = passwordVisibleChange
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = "Mostrar contraseña"
                    )
                }
            },
            visualTransformation = if (passwordVisible){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            colors = if (isValidPassword) {
                OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green
                )
            } else {
                OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red
                )
            }
        )
    }
}

//campo email
@Composable
fun RowEmail(
    email: String,
    emailChange: (String)->Unit,
    isValidEmail:Boolean
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center,) {
        OutlinedTextField(
            value = email,
            onValueChange = { emailChange(it) },
            label = {Text("Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = if (isValidEmail) {
                OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green
                )
            } else {
                OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red
                )
            }
        )
    }
}

//imagen logo
@Composable
fun RowImage(){
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