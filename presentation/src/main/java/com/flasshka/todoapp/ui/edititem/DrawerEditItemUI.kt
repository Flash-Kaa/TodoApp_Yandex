package com.flasshka.todoapp.ui.edititem

import androidx.compose.runtime.Composable
import com.flasshka.domain.entities.TodoItem

@Composable
fun DrawerEditItemUI(
    editItemVM: EditItemVM,
    item: TodoItem? = null
) {
    editItemVM.updateState(item)

    EditItemUI(
        getName = editItemVM::getName,
        getImportance = editItemVM::getImportance,
        getDeadline = editItemVM::getDeadline,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}