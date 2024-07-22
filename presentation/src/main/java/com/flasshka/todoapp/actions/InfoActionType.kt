package com.flasshka.todoapp.actions

sealed class InfoActionType {
    data object Close: InfoActionType()
}