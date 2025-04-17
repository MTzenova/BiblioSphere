package com.example.bibliosphere.presentation.register

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliosphere.core.navigation.Login
import com.example.bibliosphere.presentation.components.*
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
    ){
        Register(
            Modifier.align(Alignment.Center),
            viewModel,
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin
        )
    }

}

@Composable
fun Register(modifier: Modifier, viewModel: RegisterScreenViewModel, navigateToHome: () -> Unit, navigateToLogin: () -> Unit) {

    //definir variables
    val userName:String by viewModel.userName.observeAsState(initial="")
    val isValidUserName: Boolean by viewModel.isValidUserName.observeAsState(initial = false)
    val password:String by viewModel.password.observeAsState(initial = "")
    val isValidPassword: Boolean by viewModel.isValidPassword.observeAsState(initial = false)
    val passwordVisible: Boolean by viewModel.passwordVisible.observeAsState(initial = false)
    val email:String by viewModel.email.observeAsState(initial = "")
    val isValidEmail: Boolean by viewModel.isValidEmail.observeAsState(initial = false)
    val registerEnable:Boolean by viewModel.registerEnable.observeAsState(initial = false)

    val birthDate:String by viewModel.birthDate.observeAsState(initial = "")
    val isValidBirthDate: Boolean by viewModel.isValidBirthDate.observeAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()

    val activity = LocalContext.current as? Activity
    val context = LocalContext.current



    Column(modifier = modifier) {
        Column(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)) {
            AppIcon(width = 180.dp)
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(text = "Registro:",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start = BiblioSphereTheme.dimens.paddingNormal,
                            top = BiblioSphereTheme.dimens.paddingNormal),
                    color = colorScheme.primary
                )
            }

            Row(
                Modifier.fillMaxWidth()
                    .padding(bottom = BiblioSphereTheme.dimens.paddingNormal,
                        start = BiblioSphereTheme.dimens.paddingNormal,
                        end = BiblioSphereTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                UserName(
                    value = userName,
                    onValueChange = {viewModel.onRegisterChanged(it,email,password, birthDate)},
                    isValidUserName = isValidUserName,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(BiblioSphereTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center
            ){
                EmailTextField(
                    value = email,
                    onValueChange = {viewModel.onRegisterChanged(userName,it,password, birthDate)},
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
                    onValueChange = { viewModel.onRegisterChanged(userName,email, it, birthDate) },
                    isPasswordVisible = passwordVisible,
                    onVisibilityToggle = { viewModel.togglePasswordVisibility() },
                    isValidPassword = isValidPassword,
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Contraseña"
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(BiblioSphereTheme.dimens.paddingNormal),
                horizontalArrangement = Arrangement.Center
            ){
                DatePickerFieldToModal(
                    birthDate = birthDate,
                    onBirthDateChange = {viewModel.onRegisterChanged(userName,email,password,it)},
                    isValidDate = isValidBirthDate
                )
            }

            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))

            RowButtonRegister(registerEnable){
                navigateToHome()
            }

            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerLarge))

            RowAccount {
                navigateToLogin()
            }
        }
    }


}

//ya hay cuenta
@Composable
fun RowAccount(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun RowButtonRegister(
    registerEnable:Boolean,
    onRegisterSelected: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center){
        PrimaryButton("Registrarse",
            onClick = { onRegisterSelected() },
            enabled = registerEnable)
    }
}

@Composable
fun UserName(
    value: String,
    onValueChange: (String) -> Unit,
    isValidUserName: Boolean,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text("Nombre de usuario")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 1,
        singleLine = true,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = if(isValidUserName){
            OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.Green,
                focusedBorderColor = Color.Green
            )
        }else{
            OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.Red,
                focusedBorderColor = Color.Red
            )
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(viewModel = viewModel(), navigateToHome = {}, navigateToLogin = {})
}