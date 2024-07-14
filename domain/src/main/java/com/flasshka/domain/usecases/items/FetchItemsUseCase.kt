package com.flasshka.domain.usecases.items

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Use case for fetch items
 */
class FetchItemsUseCase(
    private val netRepository: TodoItemRepository,
    private val dbRepository: TodoItemRepository,
    private val tokenRepository: TokenRepository,
    private val onErrorAction: (suspend () -> Unit)? = null
) {
    suspend operator fun invoke() {
        runWithSupervisorInBackground(
            tryCount = 2u,
            onErrorAction = onErrorAction
        ) {
            val itemsFromLocal = dbRepository.fetchItems()

            for (waitingForAToken in 0..5) {
                if (tokenRepository.haveLogin()) {
                    val itemsFromNet = netRepository.fetchItems()
                    updateItems(itemsFromLocal, itemsFromNet)
                    break
                } else {
                    delay(2000L)
                }
            }
        }
    }

    private suspend fun updateItems(
        localItems: Flow<List<TodoItem>>,
        netItems: Flow<List<TodoItem>>
    ) {
        val local = localItems.firstOrNull()
        val network = netItems.firstOrNull()

        if (local == null || network == null) {
            return
        }

        val localMap = local.map { it.getNewItemWithUpdate(network) }
        val netMap = network.map { it.getNewItemWithUpdate(local) }

        netRepository.updateItems(localMap + network.distinctById(local))
        dbRepository.updateItems(netMap + local.distinctById(network))
    }

    private fun TodoItem.getNewItemWithUpdate(otherItems: List<TodoItem>): TodoItem {
        val fromOther = otherItems.firstOrNull { it.id == id }

        if (fromOther == null) {
            return this
        }

        val dbHaveChange = lastChange != null
        if (dbHaveChange.not()) {
            return fromOther
        }

        val isNew = fromOther.lastChange == null || fromOther.lastChange.time < lastChange!!.time

        return if (isNew) this else fromOther
    }

    private fun List<TodoItem>.distinctById(other: List<TodoItem>): List<TodoItem> {
        return filter { current -> other.all { current.id != it.id } }
    }
}