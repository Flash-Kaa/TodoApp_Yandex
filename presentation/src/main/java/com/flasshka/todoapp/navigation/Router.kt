package com.flasshka.todoapp.navigation

import androidx.navigation.NavHostController

/**
 * Managing navigation in the app
 */
class Router(val navController: NavHostController) {
    fun back() {
        navController.popBackStack()
    }

    fun navigateToListOfItems() {
        navController.navigate(NavScreen.ListOfItems.route) {
            navController.graph.setStartDestination(NavScreen.ListOfItems.route)
            back()
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
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

    fun navigateToInfo() {
        navController.navigate(NavScreen.Info.route)
    }

    fun navigateToSettings() {
        navController.navigate(NavScreen.Settings.route)
    }
}