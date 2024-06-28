package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class AddTodoItemUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.addTodoItem(todoItem)
    }
}