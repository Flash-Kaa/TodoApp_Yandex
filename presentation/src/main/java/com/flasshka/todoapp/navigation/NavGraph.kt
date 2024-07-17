package com.flasshka.todoapp.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.ui.authorization.DrawerAuthorizationUI
import com.flasshka.todoapp.ui.edititem.DrawerEditItemUI
import com.flasshka.todoapp.ui.info.DrawerInfoUI
import com.flasshka.todoapp.ui.listitems.DrawerListUI
import com.flasshka.todoapp.ui.settings.DrawerSettingsUI

@Composable
fun NavGraph(
    router: Router,
    snackbarHostState: SnackbarHostState = LocalContext.current.appComponent.provideSnackbar()
) {
    NavHost(
        navController = router.navController,
        startDestination = NavScreen.Authorization.route
    ) {
        composable(NavScreen.Authorization.route) { DrawerAuthorizationUI(router) }
        composable(NavScreen.ListOfItems.route) { DrawerListUI(router, snackbarHostState) }
        composable(NavScreen.Settings.route) { DrawerSettingsUI(router, snackbarHostState) }
        composable(NavScreen.Info.route) { DrawerInfoUI(router, snackbarHostState) }

        composable(
            "${NavScreen.EditItem.route}/{itemId}",
            listOf(navArgument("itemId") {
                type = NavType.StringType
                nullable = true
            }
            )
        ) {
            val id = it.arguments?.getString("itemId")
            DrawerEditItemUI(router, id, snackbarHostState)
        }
    }
}