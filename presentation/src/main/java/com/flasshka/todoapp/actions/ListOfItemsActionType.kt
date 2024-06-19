package com.flasshka.todoapp.actions

sealed class ListOfItemsActionType {
    data object OnCreate : ListOfItemsActionType()
}