package com.flasshka.domain.usecases

import com.flasshka.domain.interfaces.TodoItemRepository

class FetchItemsUseCase(
    private val repository: TodoItemRepository,
    private val onErrorAction: (() -> Unit)? = null
) {
    suspend operator fun invoke() {
        repository.fetchItems(onErrorAction)
    }
}