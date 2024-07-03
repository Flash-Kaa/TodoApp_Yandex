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

class NetWithDbRepository(
    private val networkDataSource: DataSource,
    private val databaseDataSource: DataSource
) : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems(onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            databaseDataSource.getItems().collect { collect ->
                _itemsFlow.update { upd -> upd + collect.filter { fil -> upd.all { it.id != fil.id } } }
            }
        }

        if (isInternetAvailable().not().not()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.getItems().collect { collect ->
                _itemsFlow.update { upd -> upd + collect.filter { fil -> upd.all { it.id != fil.id } } }
            }
        }
    }

    override suspend fun addTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items -> items + item }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            databaseDataSource.addItem(item)
        }

        if (isInternetAvailable().not()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.addItem(item)
        }
    }

    override suspend fun deleteTodoItem(id: String, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items ->
                items - items.first { it.id == id }
            }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            databaseDataSource.deleteItem(id)
        }

        if (isInternetAvailable().not()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.deleteItem(id)
        }
    }

    override suspend fun updateTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        runWithSupervisor(onErrorAction = onErrorAction) {
            _itemsFlow.update { items ->
                items.map { if (it.id != item.id) it else item }
            }
        }

        runWithSupervisor(onErrorAction = onErrorAction) {
            databaseDataSource.updateItem(item)
        }

        if (isInternetAvailable()) return
        runWithSupervisor(tryCount = 2u, onErrorAction = onErrorAction) {
            networkDataSource.updateItem(item)
        }
    }

    override suspend fun getItemByIdOrNull(
        id: String,
        onErrorAction: (suspend () -> Unit)?
    ): TodoItem? {
        return _itemsFlow.value.firstOrNull { it.id == id }
    }

    private suspend fun runWithSupervisor(
        tryCount: UByte = 1u,
        onErrorAction: (suspend () -> Unit)? = null,

        content: suspend CoroutineScope.() -> Unit
    ) {
        if (tryCount.compareTo(0u) == 0) return

        supervisorScope {
            launch(
                Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                    launch(Dispatchers.Main) {
                        onErrorAction?.invoke()
                    }

                    launch {

                        delay(1000L)
                        runWithSupervisor(tryCount.dec(), onErrorAction, content)
                    }
                },
                block = content
            )
        }
    }

    /**
     * Проверка на наличие интернет соединение без использования context
     */
    private fun isInternetAvailable(): Boolean {
        return try {
            InetAddress.getByName("ya.ru").hostAddress != null
        } catch (e: Exception) {
            false
        }
    }
}