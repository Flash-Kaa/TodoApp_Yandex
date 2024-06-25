package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class GetTodoItemByIdOrNullUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(id: String): TodoItem? {
        return repository.getTodoItems().firstOrNull { it.id == id }
    }
}