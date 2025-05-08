package com.example.bibliosphere.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bibliosphere.core.navigation.NavigationWrapper

class MainActivity : ComponentActivity() {

    //private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            //navHostController = rememberNavController()

            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

            BiblioSphereTheme{
                NavigationWrapper()
            }
        }
    }
}


