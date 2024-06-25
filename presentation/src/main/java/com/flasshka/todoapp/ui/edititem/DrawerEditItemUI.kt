package com.flasshka.todoapp.ui.edititem

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null
) {
    val editItemVM: EditItemVM = viewModel(
        factory = EditItemVM.Factory(router, itemId)
    )

    EditItemUI(
        getName = editItemVM::getName,
        getImportance = editItemVM::getImportance,
        getDeadline = editItemVM::getDeadline,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}