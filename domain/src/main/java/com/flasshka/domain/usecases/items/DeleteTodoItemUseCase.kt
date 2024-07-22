package com.flasshka.domain.usecases.items

import com.flasshka.domain.interfaces.items.TodoItemRepository
import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

/**
 * Use case for deleting element
 */
class DeleteTodoItemUseCase(
    private val netRepository: TodoItemRepository,
    private val dbRepository: TodoItemRepository,
    private val tokenRepository: TokenRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(id: String) {
        runWithSupervisorInBackground(
            tryCount = 3u,
            onErrorAction = onErrorAction
        ) {
            dbRepository.deleteTodoItem(id)

            if (tokenRepository.haveLogin()) {
                netRepository.deleteTodoItem(id)
            }
        }
    }
}