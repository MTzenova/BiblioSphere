package com.example.bibliosphere.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.example.bibliosphere.presentation.firebase.AuthViewModel
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
    scope: CoroutineScope,
    authViewModel: AuthViewModel,
    currentBackStackEntry: NavBackStackEntry?,
) {

    TopAppBar(
        title = {

            val userName by authViewModel.userNameLibrary.observeAsState()
            var titleScreen = getScreenTitle(currentBackStackEntry?.destination?.route)

            if (currentRoute?.startsWith(UserLibrary.route) == true) {
                val userId = currentBackStackEntry?.arguments?.getString("userId")
                if(userId != null) {
                    println("userid: ${userId.toString()}")
                    LaunchedEffect(userId) {
                        authViewModel.getUserName(userId)
                    }
                }

                titleScreen = "Biblioteca de $userName"
            }

            Text(
                text = titleScreen,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                fontSize = 17.sp
            )
        },
        modifier = modifier
            .statusBarsPadding(),
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
        }
        //campanita eliminada
    )
}