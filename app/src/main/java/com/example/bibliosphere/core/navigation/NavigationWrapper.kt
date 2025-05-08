package com.example.bibliosphere.core.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.home.HomeScreen
import com.example.bibliosphere.presentation.login.LoginScreen
import com.example.bibliosphere.presentation.login.LoginScreenViewModel
import com.example.bibliosphere.presentation.register.RegisterScreen
import com.example.bibliosphere.presentation.register.RegisterScreenViewModel
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBars = currentRoute != Login::class.qualifiedName && currentRoute != Register::class.qualifiedName
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold{ paddingValues ->
        Screen(
            modifier = Modifier.padding(paddingValues),
            showBars,
            scrollBehavior,
            navController,
            authViewModel,
            currentRoute,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    showBars: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    currentRoute: String?
) {
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        //esta parte no se ve en login ni en register
        topBar = {
            if (showBars) {
                TopBar(scrollBehavior = scrollBehavior, currentRoute = currentRoute)
            }
        },
        //aqui el bottomBar con el if
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Login> {
                val viewModel: LoginScreenViewModel = viewModel()
                LoginScreen(
                    viewModel = viewModel,
                    authViewModel = authViewModel,
                    navigateToHome = { navController.navigate(Home) }, //navegación a Home
                    navigateToRegister = { navController.navigate(Register) }
                )
            }
            composable<Home> {
                HomeScreen(
                    authViewModel = authViewModel,
                    navigateToLogin = { navController.navigate(Login) {
                        popUpTo(Login) { inclusive = true }
                    } },
                    paddingValues = paddingValues
                )
            }
            composable<Register> {
                val viewModel: RegisterScreenViewModel = viewModel()
                RegisterScreen(
                    viewModel = viewModel,
                    authViewModel = authViewModel,
                    navigateToHome = { navController.navigate(Home) },
                    navigateToLogin = { navController.navigate(Login) },
                )
            }
        }

    }
}


//esto quizá podria meterlo como un componente?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    currentRoute: String?
) {
    TopAppBar(
        modifier = modifier
            .padding(horizontal = BiblioSphereTheme.dimens.paddingMedium)
            .clip(RoundedCornerShape(100.dp)),
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        ),
        windowInsets = WindowInsets(top = 0.dp),
        title = {
            Text(
                text = getScreenTitle(currentRoute), //aqui estaria bien poner el titulo de la pestaña en la que estamos
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                fontSize = 17.sp
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = BiblioSphereTheme.dimens.paddingMedium, end = BiblioSphereTheme.dimens.paddingNormal)
                    .size(27.dp)
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = BiblioSphereTheme.dimens.paddingNormal)
                    .size(30.dp)
            )
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = BiblioSphereTheme.dimens.paddingNormal, end = BiblioSphereTheme.dimens.paddingMedium)
                    .size(30.dp)
            )
        }
    )
}

fun getScreenTitle(route: String?): String = when (route) {
    Home::class.qualifiedName -> "Inicio"
    Register::class.qualifiedName -> "Registro"
    Login::class.qualifiedName -> "Login"
    else -> "BiblioSphere"
}