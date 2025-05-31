package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isValidEmail: Boolean,
    modifier: Modifier = Modifier,
    ){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.email))},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = 1,
        singleLine = true,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        placeholder = { Text(stringResource(id = R.string.email_placeholder)) },
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