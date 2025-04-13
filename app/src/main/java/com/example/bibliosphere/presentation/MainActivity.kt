package com.example.bibliosphere.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import androidx.compose.ui.unit.sp
import com.example.bibliosphere.R
import com.example.bibliosphere.core.navigation.NavigationWrapper
import com.example.bibliosphere.presentation.home.HomeScreen
import com.example.bibliosphere.presentation.login.LoginScreen
import com.example.bibliosphere.presentation.login.LoginScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiblioSphereTheme{
                NavigationWrapper()
            }
        }
    }
//    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            val windowSize = calculateWindowSizeClass(activity = this)
//            BiblioSphereTheme(
//                windowSize = windowSize.widthSizeClass
//            ){
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ){
//                    //MiSegundoComposable()
//                    //HomeScreen()
//                    LoginScreen(viewModel = LoginScreenViewModel())
//                    LoginPreview()
//                }
//            }
//
//
//        }
//    }
}

@Composable
fun MiPrimerComposable() {

    Column(modifier = Modifier.fillMaxSize().padding(top=35.dp)) {
        Row{
            Text(text = "Esto es una fila")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Otra fila más")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Hello World")

        Text(text = "Hola Mundo")

    }
}

@Composable
fun MiSegundoComposable() {

    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Image(
            painter = painterResource(id = R.drawable.logo_bibliosphere),
            contentDescription = "Logo BiblioSphere",
            modifier = Modifier.align(Alignment.Center).fillMaxSize(),
        )

        Text(text = "BiblioSphere",fontSize = 20.sp, color = Color.Gray)
    }

}

@Preview(showBackground = true)
@Composable
fun MiPrimerComposablePreview(){
    MiSegundoComposable()
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen(viewModel = LoginScreenViewModel())
}
