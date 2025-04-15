package com.example.bibliosphere.presentation.login

import android.app.Activity
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
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.presentation.AuthState
import com.example.bibliosphere.presentation.AuthViewModel
import com.example.bibliosphere.presentation.components.PrimaryButton
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    authViewModel: AuthViewModel, // Recibimos el authViewModel
    navigateToHome: () -> Unit
) {
    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onSecondary)) {
        Login(
            Modifier.align(Alignment.Center),
            viewModel,
            authViewModel = authViewModel, // Aquí pasas el authViewModel
            navigateToHome = navigateToHome
        )
    }
}


@Composable
fun Login(modifier: Modifier, viewModel: LoginScreenViewModel, navigateToHome: () -> Unit, authViewModel: AuthViewModel) {

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
    val activity = LocalContext.current as? Activity

    //auth
    val authState = authViewModel.authState.observeAsState()

    //funcioanlidad de iniciar sesión y llevarnos a home
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navigateToHome()
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    if(isLoading){
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{

        //código visual
        Column(modifier = modifier) {
            Column(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)) {
                RowImage()
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Iniciar sesión:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(start = BiblioSphereTheme.dimens.paddingNormal,
                                top = BiblioSphereTheme.dimens.paddingNormal),
                        color = colorScheme.primary
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
//                        viewModel.onLoginSelected()
//                        navigateToHome()
                        //aqui iniciamos sesión
                        authViewModel.login(email, password)//después de iniciar sesión, vamos a home
                    }
                }

                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))

                RowLoginWith(
                    onGoogleClick = {
                        coroutineScope.launch {
                            try {
                                if (activity != null) {
                                    authViewModel.signInWithGoogle(activity)
                                }else {
                                    Toast.makeText(context, "No se pudo obtener la actividad", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))

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

//boton para google
@Composable
fun LoginWithButton(
    icon: Painter,
    text: String,
    textColor: Color,
    onClick: () -> Unit,
    buttonColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = "$text icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(BiblioSphereTheme.dimens.iconSizeSmall)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor)
    }
}



@Composable
fun RowLoginWith(onGoogleClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))
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
                text = "O inicia sesión con",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = colorScheme.primary
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerLarge))

        // Botón Google
        LoginWithButton(
            icon = painterResource(id = R.drawable.google_icon),
            text = "Google",
            textColor = Color.White,
            onClick = onGoogleClick,
            buttonColor = colorScheme.primary
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
        PrimaryButton("Entrar", onClick = onLoginSelected, enabled = loginEnable)
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
        .padding(bottom = BiblioSphereTheme.dimens.paddingNormal, start = BiblioSphereTheme.dimens.paddingNormal, end = BiblioSphereTheme.dimens.paddingNormal),
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
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen(
        viewModel = LoginScreenViewModel(),
        authViewModel = AuthViewModel(),
        navigateToHome = {}
    )
}