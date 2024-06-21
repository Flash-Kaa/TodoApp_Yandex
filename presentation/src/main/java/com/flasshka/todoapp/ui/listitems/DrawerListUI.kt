package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.Composable

@Composable
fun DrawerListUI(listVM: ListVM) {
    ListUI(
        getDoneCount = listVM::getDoneCount,
        getVisibilityDoneON = listVM::visibility,
        getItems = listVM::getItems,
        getAction = listVM::getAction
    )
}