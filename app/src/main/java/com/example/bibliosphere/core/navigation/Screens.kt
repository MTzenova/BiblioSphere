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

@Serializable
object BookDetail{
    const val route = "bookDetail"
    fun bookRoute(idBook:String) = "bookDetail/$idBook"
}

@Serializable
object Library

@Serializable
object UserLibrary { //copia de Library para los otros usuarios desde explore
    const val route = "userLibrary"
    fun libraryRoute(userId: String) = "userLibrary/$userId"
}

@Serializable
object Explore

@Serializable
object Profile

@Serializable
object About

//función para obtener el título según la pantalla
fun getScreenTitle(route: String?): String = when (route) {
    Home::class.qualifiedName -> "Inicio"
    Register::class.qualifiedName -> "Registro"
    Login::class.qualifiedName -> "Login"
    Search::class.qualifiedName -> "Search books"
    BookDetail::class.qualifiedName -> "Book detail"
    Library::class.qualifiedName -> "Library"
    UserLibrary::class.qualifiedName -> "User Library"
    Profile::class.qualifiedName -> "Profile"
    Explore::class.qualifiedName -> "Explore libraries"
    About::class.qualifiedName -> "About us"
    else -> "BiblioSphere"
}