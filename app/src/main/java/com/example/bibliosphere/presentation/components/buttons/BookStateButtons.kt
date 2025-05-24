package com.example.bibliosphere.presentation.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

//estados
enum class BookState {
    LEIDO, PENDIENTE, FAVORITO, LEYENDO
}

@Composable
fun BookStateButtons(
    initialStates: Set<BookState> = emptySet(),
    onStatesChanged: (Set<BookState>) -> Unit,
) {
    var currentStates by remember { mutableStateOf(initialStates) }

    fun handleStateToggle(state: BookState) {

        val updatedStates = currentStates.toMutableSet()

        if (state in updatedStates) {

            updatedStates.remove(state)

        } else {

            //solo el botón favorito puede estar marcado con otro
            if (state != BookState.FAVORITO) {

                updatedStates.removeAll(listOf(
                    BookState.LEIDO,
                    BookState.LEYENDO,
                    BookState.PENDIENTE
                ))
            }
            updatedStates.add(state)
        }

        currentStates = updatedStates
        onStatesChanged(currentStates)

    }

    //boton con icono de fav/leyendo/leido/pendiente
    @Composable
    fun StateToggleButton(
        state: BookState,
        buttonIcon: ImageVector,
        unselectedButtonIcon: ImageVector,
        description: String,
    ){
        val buttonIsSelected = state in currentStates

        IconToggleButton(
            checked = buttonIsSelected,
            onCheckedChange = { handleStateToggle(state) }
        ){
            Icon(
                imageVector = if (buttonIsSelected) buttonIcon  else unselectedButtonIcon,
                contentDescription = description,
                tint = if(buttonIsSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
            )
        }
    }

    //row con los botones de estado
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        StateToggleButton(
            state = BookState.FAVORITO,
            buttonIcon = Icons.Filled.Favorite,
            unselectedButtonIcon = Icons.Outlined.FavoriteBorder,
            description = "Marcar como favorito"
        )

        StateToggleButton(
            state = BookState.LEIDO,
            buttonIcon = Icons.Filled.CheckCircle,
            unselectedButtonIcon = Icons.Outlined.CheckCircle,
            description = "Marcar como leído"
        )

        StateToggleButton(
            state = BookState.LEYENDO,
            buttonIcon = Icons.Filled.Bookmark,
            unselectedButtonIcon = Icons.Outlined.BookmarkBorder,
            description = "Marcar como leyendo"
        )

        StateToggleButton(
            state = BookState.PENDIENTE,
            buttonIcon = Icons.Filled.AccessTimeFilled,
            unselectedButtonIcon = Icons.Outlined.AccessTime,
            description = "Marcar como pendiente"
        )

    }

}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookStateButtonsPreview() {
    BookStateButtons(
        initialStates = setOf(BookState.PENDIENTE),
        onStatesChanged = {}
    )
}