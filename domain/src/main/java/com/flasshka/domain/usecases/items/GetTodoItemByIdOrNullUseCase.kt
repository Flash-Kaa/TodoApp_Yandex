package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.items.TodoItemRepository


/**
 * Use case for getting item with id
 */
class GetTodoItemByIdOrNullUseCase(
    private val repository: TodoItemRepository
) {
    suspend operator fun invoke(id: String): TodoItem? {
        return try {
            repository.getItemById(id)
        } catch (e: Exception) {
            null
        }
    }
}