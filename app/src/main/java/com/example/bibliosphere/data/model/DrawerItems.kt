package com.example.bibliosphere.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItems(
    @StringRes val title: Int,
    val route: String,
    val icon: ImageVector
)