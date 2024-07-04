package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

/**
 * Use case for adding element
 */
class AddTodoItemUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.addTodoItem(todoItem, onErrorAction)
    }
}