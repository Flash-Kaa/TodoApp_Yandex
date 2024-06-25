package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.StateFlow

class GetTodoItemsUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(): List<TodoItem> {
        return repository.getTodoItems()
    }
}