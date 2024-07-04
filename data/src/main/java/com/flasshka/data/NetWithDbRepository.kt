package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.DataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.InetAddress
import javax.inject.Inject

/**
 * Repository for using data
 */
class NetWithDbRepository @Inject constructor(
    private val networkDataSource: DataSource,
    private val localDataSource: DataSource
) : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems(onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            val itemsFromLocal: List<TodoItem> = collectFromFlow(localDataSource)

            if (isInternetAvailable().not()) return@runWithSupervisor
            val itemsFromNet: List<TodoItem> = collectFromFlow(networkDataSource)

            updateItems(itemsFromLocal, itemsFromNet)
        }
    }

    private suspend fun updateItems(localItems: List<TodoItem>, netItems: List<TodoItem>) {
        val localMap = localItems.map { it.getNewItemWithUpdate(netItems) }
        val netMap = netItems.map { it.getNewItemWithUpdate(localItems) }

        networkDataSource.updateItems(localMap + netItems.distinctById(localItems))
        localDataSource.updateItems(netMap + localItems.distinctById(netItems))
    }

    override suspend fun addTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items -> items + item }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            localDataSource.addItem(item)
        }

        if (isInternetAvailable().not()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.addItem(item)
        }
    }

    override suspend fun deleteTodoItem(id: String, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items ->
                items - getItemById(items, id)
            }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            localDataSource.deleteItem(id)
        }

        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.deleteItem(id)
        }
    }

    override suspend fun updateTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items ->
                updateItemById(items, item)
            }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            localDataSource.updateItem(item)
        }

        if (isInternetAvailable().not()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.updateItem(item)
        }
    }

    override suspend fun getItemByIdOrNull(
        id: String,
        onErrorAction: (suspend () -> Unit)?
    ): TodoItem? = _itemsFlow.value.firstOrNull { it.id == id }

    private suspend fun runWithSupervisor(
        tryCount: UByte = 1u,
        onErrorAction: (suspend () -> Unit)? = null,

        content: suspend CoroutineScope.() -> Unit
    ) {
        if (tryCount.compareTo(0u) == 0) return

        supervisorScope {
            launch(
                context = Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                    runAfterException(onErrorAction, tryCount, content)
                },
                block = content
            )
        }
    }

    private fun CoroutineScope.runAfterException(
        onErrorAction: (suspend () -> Unit)?,
        tryCount: UByte,
        content: suspend CoroutineScope.() -> Unit
    ) {
        launch(Dispatchers.Main) {
            onErrorAction?.invoke()
        }

        launch {
            delay(1000L)
            runWithSupervisor(tryCount.dec(), onErrorAction, content)
        }
    }

    private suspend fun collectFromFlow(dataSource: DataSource): List<TodoItem> {
        var itemsFromCollect: List<TodoItem> = emptyList()
        dataSource.getItems().collect { collect ->
            itemsFromCollect = collect
            _itemsFlow.update { upd -> upd + collect.distinctById(upd) }
        }
        return itemsFromCollect
    }

    private fun List<TodoItem>.distinctById(other: List<TodoItem>): List<TodoItem> {
        return filter { current -> other.all { current.id != it.id } }
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

        val isNew = fromOther.lastChange == null || fromOther.lastChange!!.time < lastChange!!.time

        return if (isNew) this else fromOther
    }

    private fun getItemById(
        items: List<TodoItem>,
        id: String
    ) = items.first { it.id == id }

    private fun updateItemById(
        items: List<TodoItem>,
        item: TodoItem
    ) = items.map { if (it.id != item.id) it else item }

    /**
     * Проверка на наличие интернет соединение без использования context
     */
    private fun isInternetAvailable(): Boolean {
        return try {
            val ipAddress = InetAddress.getByName("ya.ru")
            ipAddress.hostAddress != null
        } catch (e: Exception) {
            false
        }
    }
}