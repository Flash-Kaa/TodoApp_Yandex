package com.flasshka.domain.usecases.items

import com.flasshka.domain.interfaces.TodoItemRepository

/**
 * Use case for fetch items
 */
class FetchItemsUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke() {
        repository.fetchItems(onErrorAction)
    }
}