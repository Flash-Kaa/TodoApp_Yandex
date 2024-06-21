package com.flasshka.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flasshka.todoapp.ui.edititem.DrawerEditItemUI
import com.flasshka.todoapp.ui.edititem.EditItemVM
import com.flasshka.todoapp.ui.listitems.DrawerListUI
import com.flasshka.todoapp.ui.listitems.ListVM

@Composable
fun NavGraph(
    navController: NavHostController,
    listVM: ListVM,
    editItemVM: EditItemVM
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.ListOfItems.route
    ) {
        composable(NavScreen.ListOfItems.route) {
            DrawerListUI(listVM = listVM)
        }

        composable(NavScreen.CreateItem.route) {
            DrawerEditItemUI(editItemVM = editItemVM)
        }

        composable(
            route = "${NavScreen.ChangeItem.route}/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("itemId")

            DrawerEditItemUI(
                editItemVM = editItemVM,
                item = if (id == null) null else listVM.getItem(id)
            )
        }
    }
}