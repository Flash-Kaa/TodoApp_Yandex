package com.flasshka.todoapp.navigation

sealed class NavScreen(val route: String) {
    data object ListOfItems : NavScreen("list_of_items")

    data object EditItem : NavScreen("change_by_id") {
        fun getRouteWithId(id: String? = null) = "$route/$id"
    }
}