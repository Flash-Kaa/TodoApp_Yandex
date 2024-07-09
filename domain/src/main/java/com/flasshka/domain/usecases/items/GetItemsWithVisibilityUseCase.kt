package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for getting items with current visibility
 */
class GetItemsWithVisibilityUseCase(
    private val repository: TodoItemRepository
) {
    operator fun invoke(visibility: Boolean): Flow<List<TodoItem>> {
        return repository.itemsFlow.map { list ->
            list.filter { item ->
                !item.completed || visibility
            }
        }
    }
}