package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerListUI(router: Router) {
    val listVM: ListVM = viewModel(
        factory = ListVM.Factory(router = router)
    )

    ListUI(
        doneCount = listVM.getDoneCount(),
        visibilityDoneON = listVM.visibility,
        items = listVM.getItems(),
        getAction = listVM::getAction
    )
}