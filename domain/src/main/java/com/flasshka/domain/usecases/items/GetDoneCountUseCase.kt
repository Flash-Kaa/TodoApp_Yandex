package com.flasshka.domain.usecases.items

import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for getting count completed items
 */
class GetDoneCountUseCase(
    private val repository: TodoItemRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.itemsFlow.map { list ->
            list.count { item -> item.completed }
        }
    }
}