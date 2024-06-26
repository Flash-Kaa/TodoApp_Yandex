package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.Composable

@Composable
fun DrawerListUI(listVM: ListVM) {
    listVM.updateList()

    ListUI(
        doneCount = listVM.getDoneCount(),
        visibilityDoneON = listVM.visibility,
        items = listVM.getItems(),
        getAction = listVM::getAction
    )
}