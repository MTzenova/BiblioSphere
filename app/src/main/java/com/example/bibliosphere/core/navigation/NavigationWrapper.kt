package com.example.bibliosphere.core.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.data.model.DrawerItems
import com.example.bibliosphere.data.model.TabDestination
import com.example.bibliosphere.presentation.bookDetail.BookDetailScreen
import com.example.bibliosphere.presentation.bookDetail.BookDetailScreenViewModel
import com.example.bibliosphere.presentation.drawerScreens.about.AboutScreen
import com.example.bibliosphere.presentation.drawerScreens.userProfile.ProfileScreen
import com.example.bibliosphere.presentation.firebase.AuthState
import com.example.bibliosphere.presentation.firebase.AuthViewModel
import com.example.bibliosphere.presentation.firebase.UserFirestoreRepository
import com.example.bibliosphere.presentation.home.HomeScreen
import com.example.bibliosphere.presentation.home.HomeScreenViewModel
import com.example.bibliosphere.presentation.library.explore.ExploreLibrariesScreen
import com.example.bibliosphere.presentation.library.explore.ExploreLibrariesScreenViewModel
import com.example.bibliosphere.presentation.library.myLibrary.MyLibraryScreen
import com.example.bibliosphere.presentation.login.LoginScreen
import com.example.bibliosphere.presentation.login.LoginScreenViewModel
import com.example.bibliosphere.presentation.register.RegisterScreen
import com.example.bibliosphere.presentation.register.RegisterScreenViewModel
import com.example.bibliosphere.presentation.search.SearchScreen
import com.example.bibliosphere.presentation.search.SearchScreenViewModel
import com.example.bibliosphere.presentation.viewmodel.MyLibraryScreenViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper() {

    //variables
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val authState = authViewModel.authState.observeAsState()

    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate(Login) {
                popUpTo(0)
            }
        }
        authViewModel.getUserImage(userId)
    }


    val currentRoute = navBackStackEntry?.destination?.route

    val showDrawer = currentRoute != Login::class.qualifiedName && currentRoute != Register::class.qualifiedName

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)


    val items = listOf(
        DrawerItems("Perfil", Icons.Default.AccountBox),
        DrawerItems("Acerca de nosotros", Icons.Filled.Info),
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
    }

}

@Composable
fun DetailedDrawer(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavHostController,
    currentRoute: String?,
    authViewModel: AuthViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    items: List<DrawerItems>
) {

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName
    val imageRes by authViewModel.userImage.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),

                ) {
                    Spacer(Modifier.height(12.dp))

                    Text("BiblioSphere", style = MaterialTheme.typography.headlineLarge)
                    HorizontalDivider()
                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier = Modifier.padding(start = 16.dp,top = 8.dp, end = 16.dp, bottom = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (userName != null) {
                            if (userName.isNotEmpty()) {
                                key(imageRes){
                                    AsyncImage(
                                        model = imageRes,
                                        contentDescription = "User profile picture",
                                        modifier = Modifier
                                            .size(94.dp)
                                            .clip(RoundedCornerShape(62.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                }

                                Text(
                                    text = userName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier
                                        .padding(top = 4.dp, bottom = 12.dp)
                                )
                            }
                        }
                    }
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
                                    else if(item.title.lowercase() == "perfil"){
                                        navController.navigate(Profile)
                                    }
                                    else if(item.title.lowercase() == "acerca de nosotros"){
                                        navController.navigate(About)
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
    val showBars = currentRoute != Login::class.qualifiedName && currentRoute != Register::class.qualifiedName
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    var selectedIndex by remember { mutableStateOf(0)}


    val tabDestinations = listOf(
        TabDestination(Library,"Library"),
        TabDestination(Explore,"Explore libraries"),
    )

    val showTabs = currentRoute in listOf(Library::class.qualifiedName, Explore::class.qualifiedName)
    var selectedTabIndex by remember { mutableStateOf(0) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState() //para arreglar bug de tabs
    val currentDestination = currentBackStackEntry?.destination?.route

    LaunchedEffect(currentDestination) { //para arreglar bug de tabs
        if (currentDestination == Library::class.qualifiedName && selectedTabIndex != 0) {
            selectedTabIndex = 0
        } else if (currentDestination == Explore::class.qualifiedName && selectedTabIndex != 1) {
            selectedTabIndex = 1
        }
    }


    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection), //sirve para ocultar el topbar al scrollear
        //esta parte no se ve en login ni en register
        topBar = {
            if (showBars) {
                Column {
                    val viewModel:AuthViewModel= viewModel()
                    TopBar(
                        scrollBehavior = scrollBehavior,
                        currentRoute = currentRoute,
                        drawerState = drawerState,
                        scope = scope,
                        authViewModel = authViewModel,
                        currentBackStackEntry = navController.currentBackStackEntryAsState().value,
                    )

                    //no sé si esto está bien así, revisar con el profesor
                    if (showTabs) {
                        TabBarNavigation(
                            destinations = tabDestinations,
                            selectedIndex = selectedTabIndex,
                            onTabSelected = { index, destination ->
                                selectedTabIndex = index
                                navController.navigate(destination.route)
                            }
                        )
                    }
                }
            }
        },
        //aqui el bottomBar con el if
        bottomBar = {

            if (showBars) {
                BottomBar(
                    navItemList = NavItemList.navItemList,
                    selectedIndex = selectedIndex,
                    onItemSelected = { index ->
                        when (index) {
                            //0 -> navController.popBackStack()
                            1 -> navController.navigate(Home)
                            2 -> if(currentRoute == Library::class.qualifiedName)selectedIndex=0 else navController.navigate(Library)
                            3 -> navController.navigate(Search)
                        }
                        if(index!= 0){
                            selectedIndex = index
                        }
                    },
                    navController = navController,
                )
            }
        }
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
                val viewModel:HomeScreenViewModel = viewModel()
                HomeScreen(navController=navController, viewModel = viewModel)
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
                SearchScreen(viewModel = viewModel, navController)
            }
            composable("${BookDetail.route}/{bookId}") { backStackEntry ->
                val viewModel: BookDetailScreenViewModel = viewModel()
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                BookDetailScreen(bookId = bookId, viewModel = viewModel)
            }
            composable<Library> { backStackEntry ->

                val viewModel: MyLibraryScreenViewModel = viewModel()
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                val actualUser = Firebase.auth.currentUser?.uid.orEmpty()

                MyLibraryScreen( userId = userId.ifEmpty {  actualUser} ,viewModel = viewModel, navController = navController)
            }
            composable("${UserLibrary.route}/{userId}"){ backStackEntry -> //copia de Library para acceder desde explore
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                val viewModel:MyLibraryScreenViewModel = viewModel()
                MyLibraryScreen( userId = userId, viewModel = viewModel, navController = navController)
            }
            composable<Explore>{
                val viewModel: ExploreLibrariesScreenViewModel = viewModel()
                ExploreLibrariesScreen(viewModel = viewModel, navController)
            }
            composable<Profile>{
                ProfileScreen()
            }
            composable<About> {
                AboutScreen()
            }
        }

    }
}

