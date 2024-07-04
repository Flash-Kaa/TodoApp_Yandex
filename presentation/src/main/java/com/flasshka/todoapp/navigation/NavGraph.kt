package com.flasshka.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.ui.edititem.DrawerEditItemUI
import com.flasshka.todoapp.ui.listitems.DrawerListUI

@Composable
fun NavGraph(
    repository: TodoItemRepository
) {
    val navController = rememberNavController()
    val router = remember { Router(navController) }
    val argsForEditScreen = listOf(
        navArgument("itemId") { type = NavType.StringType; nullable = true }
    )

    NavHost(
        navController = navController,
        startDestination = NavScreen.ListOfItems.route
    ) {
        composable(NavScreen.ListOfItems.route) { DrawerListUI(router,repository) }

        composable("${NavScreen.EditItem.route}/{itemId}", argsForEditScreen) {
            val id = it.arguments?.getString("itemId")
            DrawerEditItemUI(router, id, repository)
        }
    }
}