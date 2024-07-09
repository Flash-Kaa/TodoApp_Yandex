package com.flasshka.todoapp.navigation

import androidx.navigation.NavController

/**
 * Managing navigation in the app
 */
class Router(private val navController: NavController) {
    fun navigateToListOfItems() {
        navController.navigate(NavScreen.ListOfItems.route)
    }

    fun navigateToCreateItem() {
        navController.navigate(NavScreen.EditItem.getRouteWithId())
    }

    fun navigateToChangeItemById(id: String) {
        navController.navigate(NavScreen.EditItem.getRouteWithId(id))
    }

    fun navigateToAuthorization() {
        navController.navigate(NavScreen.Authorization.route)
    }
}