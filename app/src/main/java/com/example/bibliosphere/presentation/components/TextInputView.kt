package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LabelView(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        color = colorScheme.onSurface
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(label: String, value: String, onValueChange: (String) -> Unit, onImeAction: () -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = { Text(text = label, color = colorScheme.onSurface) },

        //label = { LabelView(title = label) },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = textFieldColors(
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            disabledTextColor = colorScheme.onSurface.copy(alpha = 0.38f),
            cursorColor = colorScheme.primary,
            focusedIndicatorColor = colorScheme.primary,
            unfocusedIndicatorColor = colorScheme.outline,
            disabledIndicatorColor = colorScheme.outline.copy(alpha = 0.38f),
            focusedLabelColor = colorScheme.primary,
            unfocusedLabelColor = colorScheme.outline,
            disabledLabelColor = colorScheme.outline.copy(alpha = 0.38f),
            focusedPlaceholderColor = colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onImeAction()
            }
        )
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextInputFieldPreview() {
    TextInputField("Hola", value = "Hola", onValueChange = {}, onImeAction = {})
}

//@Composable
//fun textFieldColors() = TextFieldDefaults.Colors(
//    textColor = Color.Black,
//    focusedLabelColor = MaterialTheme.colorScheme.primary,
//    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//    unfocusedLabelColor = Color.LightGray,
//    cursorColor = MaterialTheme.colorScheme.primary,
//    placeholderColor = Color.White,
//    disabledPlaceholderColor = Color.White,
//
//)