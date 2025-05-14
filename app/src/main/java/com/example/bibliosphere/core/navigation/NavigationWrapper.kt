package com.example.bibliosphere.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bibliosphere.data.model.DrawerItems
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.home.HomeScreen
import com.example.bibliosphere.presentation.login.LoginScreen
import com.example.bibliosphere.presentation.login.LoginScreenViewModel
import com.example.bibliosphere.presentation.register.RegisterScreen
import com.example.bibliosphere.presentation.register.RegisterScreenViewModel
import com.example.bibliosphere.presentation.search.SearchScreen
import com.example.bibliosphere.presentation.search.SearchScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper() {

    //variables
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate(Login) {
                popUpTo(0)
            }
        }
    }


    val currentRoute = navBackStackEntry?.destination?.route

    val showDrawer = currentRoute != Login::class.qualifiedName && currentRoute != Register::class.qualifiedName

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)


    val items = listOf(
        DrawerItems("Perfil", Icons.Default.AccountBox),
        DrawerItems("Ajustes", Icons.Filled.Settings), //aqui podriamos ir a activar/desactivar notificaciones, idioma...
        DrawerItems("Acerca de", Icons.Filled.Info),
        DrawerItems("Salir", Icons.AutoMirrored.Filled.Logout),

    )


    //pantalla

    //para no mostrar el drawer en login y register
    if(!showDrawer) {
        Screen(
            modifier = Modifier, //si no ponemos el padding values, se ve bien
            navController = navController,
            authViewModel = authViewModel,
            currentRoute = currentRoute,
            scope = scope,
            drawerState = drawerState,
        )
    }else{
        DetailedDrawer(
            navController = navController,
            authViewModel = authViewModel,
            currentRoute = currentRoute,
            drawerState = drawerState,
            scope = scope,
            items = items,
            content = { paddingValues ->
                Screen(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    authViewModel = authViewModel,
                    currentRoute = currentRoute,
                    scope = scope,
                    drawerState = drawerState,
                )
            }
        )

//        Scaffold{ paddingValues ->
//            //aquí el modal
//            ModalNavigationDrawer(
//                drawerState = drawerState,
//                drawerContent = {
//                    ModalDrawerSheet {
//                        items.forEachIndexed { index, item ->
//                            NavigationDrawerItem(
//                                label = {Text(item.title)},
//                                icon = {Icon(item.icon, contentDescription = null)},
//                                selected = currentRoute == item.title.lowercase(),
//                                onClick = {
//                                    scope.launch {
//                                        drawerState.close()
//                                        if(item.title.lowercase() == "salir"){
//                                            authViewModel.signout()
//                                        }else{
//                                            navController.navigate(item.title.lowercase()){
//                                                launchSingleTop = true
//                                            }
//                                        }
//                                    }
//                                }
//                            )
//                        }
//                    }
//                }
//            ) {
//
//                Screen(
//                    modifier = Modifier.padding(paddingValues),
//                    navController = navController,
//                    authViewModel = authViewModel,
//                    currentRoute = currentRoute,
//                    scope = scope,
//                    drawerState = drawerState,
//                )
//            }
//       }
    }

}

@Composable
fun DetailedDrawer(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    items: List<DrawerItems>
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text("BiblioSphere", style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()
                    items.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item.title) },
                            icon = { Icon(item.icon, contentDescription = null) },
                            selected = currentRoute == item.title.lowercase(),
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    if (item.title.lowercase() == "salir") {
                                        authViewModel.signout()
                                    }
                                    else if(item.title.lowercase() == "ajustes"){
                                        navController.navigate(Search)
                                    }
                                    else {
                                        navController.navigate(item.title.lowercase()) {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        content(PaddingValues(0.dp))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val searchViewModel: SearchScreenViewModel = viewModel()
    val query by searchViewModel.query.collectAsState()
    val showBars = currentRoute != Login::class.qualifiedName && currentRoute != Register::class.qualifiedName
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection), //sirve para ocultar el topbar al scrollear
        //esta parte no se ve en login ni en register
        topBar = {
            if (showBars) {
                TopBar(
                    scrollBehavior = scrollBehavior,
                    currentRoute = currentRoute,
                    drawerState = drawerState,
                    scope = scope,
                    isSearchScreen = currentRoute == Search::class.qualifiedName,
                    searchQuery = query,
                    onSearchQueryChange = { searchViewModel.onQueryChange(it)},
                    onSearch = { searchViewModel.searchBooks() }
                )
            }
        },
        //aqui el bottomBar con el if
//        bottomBar = {
//            if (showBars) {
//                BottomBar(navController = navController, currentRoute = currentRoute)
//            }
//        }
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
            composable<Search> {
                val viewModel: SearchScreenViewModel = viewModel()
                SearchScreen(viewModel = viewModel)
            }
        }

    }
}

