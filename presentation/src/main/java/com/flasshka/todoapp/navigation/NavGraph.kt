package com.flasshka.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flasshka.todoapp.ui.edititem.DrawerEditItemUI
import com.flasshka.todoapp.ui.listitems.DrawerListUI

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val router = remember { Router(navController) }

    NavHost(
        navController = navController,
        startDestination = NavScreen.ListOfItems.route
    ) {
        composable(NavScreen.ListOfItems.route) {
            DrawerListUI(router = router)
        }

        composable(
            route = "${NavScreen.EditItem.route}/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType; nullable = true }
            )
        ) {
            val id = it.arguments?.getString("itemId")

            DrawerEditItemUI(
                itemId = id,
                router = router
            )
        }
    }
}