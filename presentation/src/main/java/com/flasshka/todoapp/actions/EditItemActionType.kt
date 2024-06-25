package com.flasshka.todoapp.actions

import com.flasshka.domain.entities.TodoItem

sealed class EditItemActionType {
    // Navigation
    data object OnExit : EditItemActionType()
    data object OnSave : EditItemActionType()
    data object OnDelete : EditItemActionType()

    // General
    data class OnNameChanged(val newValue: String) : EditItemActionType()
    data class OnImportanceChanged(val newValue: TodoItem.Importance) : EditItemActionType()
    data class OnDeadlineChanged(val newValue: Long?) : EditItemActionType()
}