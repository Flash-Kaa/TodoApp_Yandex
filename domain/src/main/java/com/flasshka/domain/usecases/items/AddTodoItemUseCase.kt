package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.items.TodoItemRepository
import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

/**
 * Use case for adding element
 */
class AddTodoItemUseCase(
    private val netRepository: TodoItemRepository,
    private val dbRepository: TodoItemRepository,
    private val tokenRepository: TokenRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        runWithSupervisorInBackground(
            tryCount = 3u,
            onErrorAction = onErrorAction
        ) {
            dbRepository.addTodoItem(todoItem)

            if (tokenRepository.haveLogin()) {
                netRepository.addTodoItem(todoItem)
            }
        }
    }
}