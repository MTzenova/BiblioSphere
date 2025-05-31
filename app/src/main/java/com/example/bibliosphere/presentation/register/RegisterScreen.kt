package com.example.bibliosphere.presentation.register

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.components.*
import com.example.bibliosphere.presentation.components.buttons.PrimaryButton
import com.example.bibliosphere.presentation.components.textField.DatePickerFieldToModal
import com.example.bibliosphere.presentation.components.textField.EmailTextField
import com.example.bibliosphere.presentation.components.textField.PasswordTextField
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel,
    authViewModel: AuthViewModel,
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
            authViewModel,
            navigateToLogin = navigateToLogin
        )
    }

}

@Composable
fun Register(modifier: Modifier, viewModel: RegisterScreenViewModel, navigateToHome: () -> Unit, authViewModel: AuthViewModel, navigateToLogin: () -> Unit) {

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

    //auth
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value){
        when(authState.value) {
            is AuthState.Authenticated -> navigateToHome()
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(modifier = modifier) {
        Column(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)) {
            AppIcon(width = 180.dp)
            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(text = stringResource(id = R.string.register_title),
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
                    text = stringResource(R.string.password),
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
                    isValidDate = isValidBirthDate,
                    enabled = true
                )
            }

            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerLarge))

            RowButtonRegister(registerEnable){
                authViewModel.createAccountWithEmail(email,password, userName, birthDate)
                //navigateToHome()
            }

            Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerMedium))

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
                text = stringResource(R.string.login),
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
        PrimaryButton(
            stringResource(R.string.register_button),
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
    val maxChars = 40
    val userNameValue = if(value.length > maxChars) value.take(maxChars) else value

    OutlinedTextField(
        value = userNameValue,
        onValueChange = { newValue ->
            if (newValue.length <= maxChars) {
                onValueChange(newValue)
            }
                                        },
        label = {Text(stringResource(R.string.user_name))},
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
    RegisterScreen(viewModel = viewModel(), authViewModel = AuthViewModel(),navigateToHome = {}, navigateToLogin = {})
}