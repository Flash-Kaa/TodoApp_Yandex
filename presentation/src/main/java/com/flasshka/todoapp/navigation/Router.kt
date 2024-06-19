package com.flasshka.todoapp.navigation

import androidx.navigation.NavController

class Router(private val navController: NavController) {
    fun getNavigateToListOfItems(): () -> Unit {
        return { navController.navigate(NavScreen.ListOfItems.route) }
    }

    fun getNavigateToCreateItem(): () -> Unit {
        return { navController.navigate(NavScreen.CreateItem.route) }
    }

    fun getNavigateToChangeItemById(): (String) -> Unit {
        return { navController.navigate(NavScreen.ChangeItem.getRouteWithId(it)) }
    }
}