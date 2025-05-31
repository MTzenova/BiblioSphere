package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R


@Composable
fun TextFieldDataUser(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    label: String,
    editable: Boolean,

    ) {

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        leadingIcon = {
            leadingIcon?.let { icon ->
                Icon(icon, contentDescription = stringResource(R.string.icon), tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        label = { Text(label, color = MaterialTheme.colorScheme.onPrimary) },
        shape = RoundedCornerShape(20.dp),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
        ),
        enabled = editable
    )
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
private fun TextFieldDataUserPreview() {
    val text = remember { mutableStateOf("Nombre de usuario") }

    TextFieldDataUser(
        value = text.value,
        onValueChange = { text.value = it },
        label = "Nombre",
        leadingIcon = Icons.Default.Person,
        modifier = Modifier.padding(16.dp),
        editable = false
    )
}
