package com.example.bibliosphere.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RatingBooks( averageRating: Float?, maxRating: Int = 5){
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "$averageRating",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "/ $maxRating",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background,
        )
    }
}