package com.example.bibliosphere.core.navigation

import com.example.bibliosphere.R
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
fun getScreenTitle(route: String?): Int = when (route) {
    Home::class.qualifiedName -> R.string.home
    Register::class.qualifiedName -> R.string.register
    Login::class.qualifiedName -> R.string.login
    Search::class.qualifiedName -> R.string.search_books
    BookDetail::class.qualifiedName -> R.string.book_detail
    Library::class.qualifiedName -> R.string.library
    UserLibrary::class.qualifiedName -> R.string.user_library
    Profile::class.qualifiedName -> R.string.profile
    Explore::class.qualifiedName -> R.string.explore
    About::class.qualifiedName -> R.string.about
    else -> R.string.app_name
}