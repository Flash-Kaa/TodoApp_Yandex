package com.flasshka.todoapp.actions

sealed class ListOfItemsActionType {
    // Navigation
    data object OnCreate : ListOfItemsActionType()
    data class OnChangeItem(val id: String) : ListOfItemsActionType()

    // General
    data object OnChangeDoneVisibility : ListOfItemsActionType()

    // Interaction with items
    data class OnChangeDoneItem(val id: String) : ListOfItemsActionType()
    data class OnDeleteItem(val id: String) : ListOfItemsActionType()
}