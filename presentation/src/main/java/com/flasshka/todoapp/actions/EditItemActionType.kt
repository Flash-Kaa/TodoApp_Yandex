package com.flasshka.todoapp.actions

import com.flasshka.domain.entities.TodoItem

sealed class EditItemActionType {
    data object OnExit : EditItemActionType()
    data object OnSave : EditItemActionType()
    data object OnDelete : EditItemActionType()
    data class OnNameChanged(val newVale: String) : EditItemActionType()
    data class OnImportanceChanged(val newValue: TodoItem.Importance) : EditItemActionType()
    data class OnDeadlineChanged(val newValue: Long?) : EditItemActionType()
}