package com.flasshka.domain.usecases

import com.flasshka.domain.interfaces.TodoItemRepository

class DeleteTodoItemUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteTodoItem(id)
    }
}