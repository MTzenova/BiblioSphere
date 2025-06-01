package com.example.bibliosphere.presentation.login

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.components.*
import com.example.bibliosphere.presentation.components.buttons.IconPrimaryButton
import com.example.bibliosphere.presentation.components.buttons.PrimaryButton
import com.example.bibliosphere.presentation.components.textField.EmailTextField
import com.example.bibliosphere.presentation.components.textField.PasswordTextField
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import java.util.*

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(colorScheme.background)
            .padding(BiblioSphereTheme.dimens.paddingMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Login(
            modifier = Modifier.fillMaxWidth(),
            viewModel = viewModel,
            authViewModel = authViewModel,
            navigateToHome = navigateToHome,
            navigateToRegister = navigateToRegister
        )
    }
}


@Composable
fun Login(
    modifier: Modifier,
    viewModel: LoginScreenViewModel,
    navigateToHome: () -> Unit,
    authViewModel: AuthViewModel,
    navigateToRegister: () -> Unit
) {

    //definir variables
    val email: String by viewModel.email.observeAsState(initial = "")
    val isValidEmail by viewModel.isValidEmail.observeAsState(false)
    val password: String by viewModel.password.observeAsState(initial = "")
    val passwordVisible by viewModel.passwordVisible.observeAsState(initial = false)
    val isValidPassword by viewModel.isValidPassword.observeAsState(false)
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)

    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    //auth
    val authState = authViewModel.authState.observeAsState()

    //para el alert dialog
    val showDialog = remember { mutableStateOf(false) }

    val textEmailSent = stringResource(R.string.reset_email_sent)


    //funcioanlidad de iniciar sesión y llevarnos a home
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navigateToHome()
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxWidth().height(400.dp)) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        val textErrorActivity = stringResource(id = R.string.no_activity_error)
        //código visual

        Column(Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingMedium),
            ) {


                AppIcon(width = 180.dp)
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.login).uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(
                                start = BiblioSphereTheme.dimens.paddingNormal,
                                top = BiblioSphereTheme.dimens.paddingNormal
                            ),
                        color = colorScheme.onBackground
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
                ) {
                    EmailTextField(
                        value = email,
                        onValueChange = { viewModel.onLoginChanged(it, password) },
                        isValidEmail = isValidEmail,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(BiblioSphereTheme.dimens.paddingNormal),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PasswordTextField(
                        value = password,
                        onValueChange = { viewModel.onLoginChanged(email, it) },
                        isPasswordVisible = passwordVisible,
                        onVisibilityToggle = { viewModel.togglePasswordVisibility() },
                        isValidPassword = isValidPassword,
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.password)
                    )
                }

                RowForgottenPassword {
                    showDialog.value = true
                }

                RowButtonLogin(loginEnable) {
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
                                } else {
                                    Toast.makeText(context, textErrorActivity, Toast.LENGTH_SHORT).show()
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

    //alert dialog de olvidar contraseña
    if(showDialog.value){
        val titleText = stringResource(R.string.forgotten_password)
        ForgottenPasswordDialog(
            onDismissRequest = { showDialog.value = false },
            onResetPassword = {emailToSend ->
                authViewModel.resetPassword(emailToSend)
                showDialog.value = false
                Toast.makeText(context,textEmailSent, Toast.LENGTH_SHORT).show()
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
                text = stringResource(R.string.no_account),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = colorScheme.onBackground,
                textDecoration = TextDecoration.Underline
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
                    .padding(end = BiblioSphereTheme.dimens.paddingNormal),
                thickness = 1.dp,
                color = Color.Gray
            )

            Text(
                text = stringResource(R.string.login_with),
                modifier = Modifier.padding(horizontal = BiblioSphereTheme.dimens.paddingNormal),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = colorScheme.onBackground
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = BiblioSphereTheme.dimens.paddingNormal),
                thickness = 1.dp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerLarge))

        IconPrimaryButton(
            icon =  painterResource(id = R.drawable.google_icon),
            text = stringResource(R.string.google),
            onClick = onGoogleClick,
            textColor = colorScheme.surface,
            buttonColor = colorScheme.onSurface,
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
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = colorScheme.onBackground,
                textDecoration = TextDecoration.Underline
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
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = BiblioSphereTheme.dimens.paddingNormal),
                color = colorScheme.onSurface
            )
        },
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
        shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeExtraLarge),
        tonalElevation = BiblioSphereTheme.dimens.cardElevation,
        confirmButton = {
            TextButton(onClick = { onResetPassword(email) },
                enabled = isValidEmail
            ){
                Text(stringResource(R.string.send))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest){
                Text(stringResource(R.string.cancel))
            }
        },
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

        PrimaryButton(
            stringResource(R.string.enter),
            onClick = onLoginSelected,
            enabled = loginEnable,
            buttonColor = ButtonDefaults.buttonColors(
                containerColor = colorScheme.onBackground,
                contentColor = colorScheme.background
            ),
        )
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