package com.example.bibliosphere.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bibliosphere.presentation.AuthViewModel
import com.example.bibliosphere.presentation.home.HomeScreen
import com.example.bibliosphere.presentation.login.LoginScreen
import com.example.bibliosphere.presentation.login.LoginScreenViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            val viewModel: LoginScreenViewModel = viewModel()
            LoginScreen(
                viewModel = viewModel,
                authViewModel = authViewModel,  // Pasas authViewModel a LoginScreen
                navigateToHome = { navController.navigate(Home) } // Navegación a Home
            )
        }
        composable<Home> {
            HomeScreen(authViewModel = authViewModel) { navController.navigate(Login)}
        }
    }
}