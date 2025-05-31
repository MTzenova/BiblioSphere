package com.example.bibliosphere.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val borderNormal: Dp = 4.dp,
    val buttonHeightSmall: Dp = 46.dp,
    val buttonHeightNormal: Dp = 56.dp,
    val iconSizeSmall: Dp = 24.dp,
    val iconSizeNormal: Dp = 36.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingNormal: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val roundedShapeNormal: Dp = 8.dp,
    val roundedShapeMedium: Dp = 12.dp,
    val roundedShapeLarge: Dp = 16.dp,
    val roundedShapeExtraLarge: Dp = 20.dp,
    val spacerSmall: Dp = 4.dp,
    val spacerNormal: Dp = 8.dp,
    val spacerMedium: Dp = 16.dp,
    val spacerLarge: Dp = 40.dp,
    val cardElevation: Dp = 4.dp,
    )

val DefaultDimens = Dimens()

val TabletDimens = Dimens(
    buttonHeightSmall = 54.dp,
    buttonHeightNormal = 64.dp,
    iconSizeSmall = 24.dp,
    iconSizeNormal = 48.dp,
    paddingSmall = 8.dp,
    paddingNormal = 16.dp,
    paddingMedium = 24.dp,
    roundedShapeNormal = 16.dp,
    roundedShapeMedium = 20.dp,
    roundedShapeLarge = 24.dp,
    roundedShapeExtraLarge = 28.dp,
    spacerSmall = 8.dp,
    spacerNormal = 16.dp,
    spacerMedium = 24.dp,
    spacerLarge = 56.dp,
    cardElevation = 16.dp,
)