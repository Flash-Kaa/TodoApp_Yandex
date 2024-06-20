package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class DeleteTodoItemUseCase(
    private val repository: TodoItemRepository
) {
    fun invoke(id: String) {
        repository.deleteTodoItem(id)
    }
}