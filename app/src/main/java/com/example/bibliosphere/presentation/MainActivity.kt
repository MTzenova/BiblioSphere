package com.example.bibliosphere.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliosphere.core.navigation.NavigationWrapper
import com.example.bibliosphere.presentation.home.HomeScreen

class MainActivity : ComponentActivity() {

    //private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            //navHostController = rememberNavController()

            //val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

            BiblioSphereTheme{
                NavigationWrapper()
            }
        }
    }
}

