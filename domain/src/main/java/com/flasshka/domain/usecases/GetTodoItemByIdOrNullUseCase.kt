package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class GetTodoItemByIdOrNullUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (() -> Unit)? = null
) {
    suspend operator fun invoke(id: String): TodoItem? {
        return repository.getItemByIdOrNull(id, onErrorAction)
    }
}