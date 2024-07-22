package com.flasshka.todoapp.actions

import com.flasshka.domain.entities.Token

sealed class AuthorizationActionType {
    data object OnExitAuth : AuthorizationActionType()

    data class OnGetAnswer(val token: Token) : AuthorizationActionType()
}