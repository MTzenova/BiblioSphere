package com.example.bibliosphere.presentation.login

import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.presentation.AuthState
import com.example.bibliosphere.presentation.AuthViewModel
import com.example.bibliosphere.presentation.components.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
) {
    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onSecondary)) {
        Login(
            Modifier.align(Alignment.Center),
            viewModel,
            authViewModel = authViewModel,
            navigateToHome = navigateToHome,
            navigateToRegister = navigateToRegister
        )
    }
}


@Composable
fun Login(modifier: Modifier, viewModel: LoginScreenViewModel, navigateToHome: () -> Unit, authViewModel: AuthViewModel, navigateToRegister: () -> Unit) {

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

    //para el alert dialog
    val showDialog = remember { mutableStateOf(false) }

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
                AppIcon(width = 180.dp)
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

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = BiblioSphereTheme.dimens.paddingNormal,
                            start = BiblioSphereTheme.dimens.paddingNormal,
                            end = BiblioSphereTheme.dimens.paddingNormal
                        ),
                    horizontalArrangement = Arrangement.Center
                ){
                    EmailTextField(
                        value = email,
                        onValueChange = {viewModel.onLoginChanged(it,password)},
                        isValidEmail = isValidEmail,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(BiblioSphereTheme.dimens.paddingNormal),
                    horizontalArrangement = Arrangement.Center
                ){
                    PasswordTextField(
                        value = password,
                        onValueChange = { viewModel.onLoginChanged(email, it) },
                        isPasswordVisible = passwordVisible,
                        onVisibilityToggle = { viewModel.togglePasswordVisibility() },
                        isValidPassword = isValidPassword,
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Contraseña"

                    )

                }


                RowForgottenPassword{
                    showDialog.value = true
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
                    navigateToRegister()
                    //Toast.makeText(context, "Función no implementada aún", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //alert dialog de olvidar contraseña
    if(showDialog.value){
        val titleText = "No te preocupes, te enviaremos un enlace de recuperación"
        ForgottenPasswordDialog(
            onDismissRequest = { showDialog.value = false },
            onResetPassword = {emailToSend ->
                authViewModel.resetPassword(emailToSend)
                showDialog.value = false
                Toast.makeText(context,"Correo enviado, revisa tu bandeja de entrada", Toast.LENGTH_SHORT).show()
            },
            dialogTitle = titleText
        )
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

        IconPrimaryButton(
            icon =  painterResource(id = R.drawable.google_icon),
            text = "Google",
            onClick = onGoogleClick,
            textColor = Color.White,
            buttonColor = colorScheme.primary,
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

@Composable
fun ForgottenPasswordDialog(
    onDismissRequest: () -> Unit,
    onResetPassword: (String) -> Unit,
    dialogTitle: String,
){
    var email by remember { mutableStateOf("") }
    val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    AlertDialog(
        title = { Text(text = dialogTitle, style = MaterialTheme.typography.bodyLarge) },
        text = {
            Column{
                EmailTextField(
                    value = email,
                    onValueChange = { email = it },
                    isValidEmail = isValidEmail,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onResetPassword(email) },
                enabled = isValidEmail
            ){
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest){
                Text("Cancelar")
            }
        }
    )
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

        PrimaryButton("Entrar",
            onClick = onLoginSelected,
            enabled = loginEnable)
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen(
        viewModel = LoginScreenViewModel(),
        authViewModel = AuthViewModel(),
        navigateToHome = {},
        navigateToRegister = {}
    )
}