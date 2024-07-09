package com.flasshka.todoapp.actions

import com.flasshka.domain.entities.Token

sealed class AuthorizationActions {
    data object OnExitAuth : AuthorizationActions()

    data class OnGetAnswer(val token: Token) : AuthorizationActions()
}