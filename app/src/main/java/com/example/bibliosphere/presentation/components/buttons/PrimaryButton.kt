package com.example.bibliosphere.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//boton reutilizable para login y register
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        colors = buttonColor
    ) {
        Text(text = text,style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
    }
} //podemos ver para añadir estilos y eso más adelante

@Composable
fun ProfileButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        colors = buttonColor
    ) {
        Text(text = text,style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
    }
}