package com.example.bibliosphere.core.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    currentRoute: String?,
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    TopAppBar(
        title = {
            Text(
                text = getScreenTitle(currentRoute),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                fontSize = 17.sp
            )
        },
        modifier = modifier
            .padding(horizontal = BiblioSphereTheme.dimens.paddingMedium)
            .clip(RoundedCornerShape(100.dp)),
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        ),
        windowInsets = WindowInsets(top = 0.dp),

        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(
                            start = BiblioSphereTheme.dimens.paddingMedium,
                            end = BiblioSphereTheme.dimens.paddingNormal
                        )
                        .size(27.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Notifications action */ }) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notificaciones",
                    modifier = Modifier
                        .padding(end = BiblioSphereTheme.dimens.paddingNormal)
                        .size(27.dp)
                )
            }
        }
    )
}