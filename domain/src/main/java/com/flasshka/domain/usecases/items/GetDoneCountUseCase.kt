package com.flasshka.domain.usecases.items

import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Use case for getting count completed items
 */
class GetDoneCountUseCase(
    private val dbRepository: TodoItemRepository,
) {
    private val _items = runBlocking { dbRepository.fetchItems() }

    operator fun invoke(): Flow<Int> {
        return _items.map { it.count { item -> item.completed } }
    }
}