package com.example.bibliosphere.presentation.login

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathHitTester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Preview(showBackground = true,showSystemUi = true)
@Composable
fun LoginScreen() {
    //definir variables
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }


    //código visual
    Box(Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.align(Alignment.Center)
            .padding(BiblioSphereTheme.dimens.paddingMedium)
            .fillMaxWidth()){
            Card(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium),
                shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeNormal),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = BiblioSphereTheme.dimens.spacerMedium)
            ) {
                Column(Modifier.padding(BiblioSphereTheme.dimens.paddingMedium)) {
                    RowImage()
                    RowEmail(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        isValidEmail = isValidEmail
                    )
                    RowPassword(
                        password = password,
                        passwordChange = {
                            password = it
                            isValidPassword = password.length >= 6
                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = {passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword
                    )
                    RowButtonLogin(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword
                    )
                }
            }
        }
    }
}

//boton login
@Composable
fun RowButtonLogin(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean
    ){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center){
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {login(context)},
            enabled = isValidEmail && isValidPassword
        ){
            Text(text = "Iniciar Sesión")
        }
    }

}

//mensaje toast
fun login(context: Context){
    Toast.makeText(context, "Login de mentira", Toast.LENGTH_LONG).show()
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
    Row(Modifier.fillMaxWidth()
        .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = password,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = {Text("Contraseña")},
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
    Row(Modifier.fillMaxWidth()
        .padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center,) {
        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            label = {Text("Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
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
    Row(Modifier.fillMaxWidth().padding(BiblioSphereTheme.dimens.paddingNormal),
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id=R.drawable.logo_bibliosphere),
            contentDescription = "Imagen login",
        )
    }
}


//@Composable
//fun Login(modifier: Modifier) {
//    Column(modifier = modifier) {
//        HeaderImage()
//    }
//}
//
//
//@Composable
//fun HeaderImage() {
//    Image(painter= painterResource(id = R.drawable.logo_bibliosphere), contentDescription = "Header")
//}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EmailField() {
//    TextField(value = "", onValueChange = {},
//        Modifier.fillMaxWidth(),
//        placeholder = {Text("email@example.com")},
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//        singleLine = true, //para que no se amplie el componente
//        maxLines = 1,
//
//    )
//}