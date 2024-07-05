package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

/**
 * Use case for updating element
 */
class UpdateTodoItemUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(item: TodoItem) {
        repository.updateTodoItem(item, onErrorAction)
    }
}