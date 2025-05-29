package com.example.bibliosphere.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    buttonColor: Color,
    onTap: () -> Unit = {}
) {
    Button( //contenedor
        modifier = modifier.height(BiblioSphereTheme.dimens.buttonHeightSmall),
        shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = {onTap()},
    ){
        Text( //texto
            text = text,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}