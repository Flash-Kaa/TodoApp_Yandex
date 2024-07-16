package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.items.TodoItemRepository
import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

/**
 * Use case for updating element
 */
class UpdateTodoItemUseCase(
    private val netRepository: TodoItemRepository,
    private val dbRepository: TodoItemRepository,
    private val tokenRepository: TokenRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(item: TodoItem) {
        runWithSupervisorInBackground(
            tryCount = 3u,
            onErrorAction = onErrorAction
        ) {
            dbRepository.updateTodoItem(item)

            if (tokenRepository.haveLogin()) {
                netRepository.updateTodoItem(item)
            }
        }
    }
}