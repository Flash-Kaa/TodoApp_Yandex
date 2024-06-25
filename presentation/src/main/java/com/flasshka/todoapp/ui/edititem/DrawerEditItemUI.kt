package com.flasshka.todoapp.ui.edititem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.domain.entities.EditTodoItemState
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null
) {
    val editItemVM: EditItemVM = viewModel(
        factory = EditItemVM.Factory(router, itemId)
    )

    // val state: EditTodoItemState by editItemVM.state.collectAsState(initial = EditTodoItemState.getNewState())

    EditItemUI(
        state1 = editItemVM.state,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}