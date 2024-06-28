package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class UpdateTodoItemUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(item: TodoItem) {
        repository.updateTodoItemById(item)
    }
}