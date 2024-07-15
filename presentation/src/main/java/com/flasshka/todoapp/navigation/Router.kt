package com.flasshka.todoapp.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.flasshka.todoapp.InfoActivity

/**
 * Managing navigation in the app
 */
class Router(val navController: NavHostController) {
    fun navigateToListOfItems() {
        navController.navigate(NavScreen.ListOfItems.route) {
            navController.graph.setStartDestination(NavScreen.ListOfItems.route)
            navController.popBackStack()
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

    fun navigateToInfo(context: Context) {
        val intent = Intent(context, InfoActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToSettings() {
        navController.navigate(NavScreen.Settings.route)
    }
}