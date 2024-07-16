package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.items.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Use case for getting items with current visibility
 */
class GetItemsWithVisibilityUseCase(
    private val dbRepository: TodoItemRepository
) {
    private val _items = runBlocking { dbRepository.fetchItems() }

    operator fun invoke(visibility: Boolean): Flow<List<TodoItem>> {
        return _items.map { items ->
            items.filter { item ->
                !item.completed || visibility
            }
        }
    }
}