package com.example.bibliosphere.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bibliosphere.data.model.DrawerItems


@Composable
fun BottomBar(
    navItemList: List<DrawerItems>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavHostController,
) {
    NavigationBar {
        navItemList.forEachIndexed { index, navItem ->
            val isBack = index == 0
            val enabled = if (isBack) {
                navController.previousBackStackEntry != null
            } else true
            val selectedItem = if (isBack) false else selectedIndex == index

            NavigationBarItem(
                enabled = enabled,
                selected = selectedItem,
                onClick = {
                    if (isBack && enabled) {
                        navController.popBackStack()
                    } else if (!isBack) {
                        onItemSelected(index)
                    }
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "Icon")
                },
                label = {
                    Text(text = navItem.title)
                },
            )
        }
    }
}

object NavItemList{
    val navItemList = listOf(
        DrawerItems("Atrás", Icons.AutoMirrored.Filled.ArrowBack),
        DrawerItems("Inicio",Icons.Default.Home),
        DrawerItems("Biblioteca",Icons.Default.Book),
        DrawerItems("Buscar",Icons.Default.Search),
    )
}