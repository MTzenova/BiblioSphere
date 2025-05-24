package com.example.bibliosphere.presentation.components.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme


@Composable
fun IconPrimaryButton(
    icon: Painter,
    text: String,
    textColor: Color,
    onClick: () -> Unit,
    buttonColor: Color,

){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = buttonColor)
    ){
        Icon(
            painter = icon,
            contentDescription = "$text icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(BiblioSphereTheme.dimens.iconSizeSmall)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor)
    }
}