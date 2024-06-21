package com.flasshka.todoapp.navigation

import androidx.navigation.NavController

class Router(private val navController: NavController) {
    fun navigateToListOfItems() {
        navController.navigate(NavScreen.ListOfItems.route)
    }

    fun navigateToCreateItem() {
        navController.navigate(NavScreen.CreateItem.route)
    }

    fun navigateToChangeItemById(id: String) {
        navController.navigate(NavScreen.ChangeItem.getRouteWithId(id))
    }
}