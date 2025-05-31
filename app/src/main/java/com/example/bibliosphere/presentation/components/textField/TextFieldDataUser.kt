package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth().horizontalScroll(scrollState),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = 1,
        leadingIcon = {
            leadingIcon?.let { icon ->
                Icon(icon, contentDescription = stringResource(R.string.icon), tint = if(editable)MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background)
            }
        },
        label = { Text(label, color =if(editable) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.background) },
        shape = RoundedCornerShape(20.dp),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Start,
            color = if(editable) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
        ),

        colors =  OutlinedTextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.secondary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
        ),
        enabled = editable,
        readOnly = !editable,
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
