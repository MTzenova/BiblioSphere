package com.example.bibliosphere.core.navigation

import kotlinx.serialization.Serializable

//se definen todas las vistas
@Serializable
object Login

@Serializable
object Home

@Serializable
object Register

@Serializable
object Search

//función para obtener el título según la pantalla
fun getScreenTitle(route: String?): String = when (route) {
    Home::class.qualifiedName -> "Inicio"
    Register::class.qualifiedName -> "Registro"
    Login::class.qualifiedName -> "Login"
    Search::class.qualifiedName -> "Search"
    else -> "BiblioSphere"
}