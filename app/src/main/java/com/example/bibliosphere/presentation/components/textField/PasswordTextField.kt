package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    isValidPassword: Boolean,
    modifier: Modifier = Modifier,
    text:String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        modifier = modifier,
        label = { Text(text) },
        shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeExtraLarge),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (isPasswordVisible) {
                Icons.Filled.VisibilityOff
            } else{
                Icons.Filled.Visibility
            }
            IconButton(onClick = onVisibilityToggle) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.show_password)
                )
            }
        },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        textStyle = TextStyle(
            textAlign = TextAlign.Start,
        ),
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
        },
    )
}
