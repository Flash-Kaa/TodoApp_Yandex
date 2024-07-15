package com.flasshka.todoapp.navigation

/**
 * Key-value storage for the screen and the route to it
 */
sealed class NavScreen(val route: String) {
    data object ListOfItems : NavScreen("list_of_items")

    data object EditItem : NavScreen("change_by_id") {
        fun getRouteWithId(id: String? = null) = "$route/$id"
    }

    data object Authorization : NavScreen("authorization")

    data object Settings: NavScreen("settings")
}