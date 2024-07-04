package com.flasshka.domain.usecases

import com.flasshka.domain.interfaces.TodoItemRepository

/**
 * Use case for deleting element
 */
class DeleteTodoItemUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(id: String) {
        repository.deleteTodoItem(id, onErrorAction)
    }
}