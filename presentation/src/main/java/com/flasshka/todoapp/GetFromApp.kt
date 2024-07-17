package com.flasshka.todoapp

import android.content.Context
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.todoapp.di.components.AppComponent

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is TodoApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }

internal val Context.itemsRepository: TodoItemRepository
    get() = when (this) {
        is TodoApp -> this.itemsRepository
        else -> this.applicationContext.itemsRepository
    }

internal val Context.tokenRepository: TokenRepository
    get() = when (this) {
        is TodoApp -> this.tokenRepository
        else -> this.applicationContext.tokenRepository
    }