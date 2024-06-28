package com.flasshka.domain.usecases

import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDoneCountUseCase(
    private val repository: TodoItemRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.itemsFlow.map { list ->
            list.count { item -> item.completed }
        }
    }
}