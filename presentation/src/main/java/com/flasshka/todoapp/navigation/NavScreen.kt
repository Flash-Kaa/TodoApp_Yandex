package com.flasshka.todoapp.navigation

sealed class NavScreen(val route: String) {
    data object ListOfItems : NavScreen("list_of_items")

    data object CreateItem : NavScreen("create_item")

    data object ChangeItem : NavScreen("change_by_id") {
        fun getRouteWithId(id: String) = "$route/$id"
    }
}