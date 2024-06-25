package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.StateFlow

class GetTodoItemFlowUseCase(
    private val repository: TodoItemRepository
) {
    operator fun invoke(): StateFlow<List<TodoItem>> {
        return repository.itemsFlow
    }
}