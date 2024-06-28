package com.flasshka.domain.usecases

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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