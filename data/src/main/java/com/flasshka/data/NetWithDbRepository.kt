package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.DataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.InetAddress

class NetWithDbRepository(
    private val networkDataSource: DataSource? = null,
    private val databaseDataSource: DataSource? = null
) : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems(onErrorAction: (() -> Unit)?) {
        runWithSupervisor(onErrorAction) {
            databaseDataSource?.getItems()?.collect { collect ->
                _itemsFlow.update { upd -> upd + collect.filter { it !in upd } }
            }
        }

        runWithSupervisor(onErrorAction) {
            if (isInternetAvailable().not()) return@runWithSupervisor

            networkDataSource?.getItems()?.collect { collect ->
                _itemsFlow.update { upd -> upd + collect.filter { it !in upd } }
            }
        }
    }

    override suspend fun addTodoItem(item: TodoItem, onErrorAction: (() -> Unit)?) {
        runWithSupervisor(onErrorAction) {
            _itemsFlow.update { items -> items + item }

            databaseDataSource?.addItem(item)

            if (isInternetAvailable()) {
                networkDataSource?.addItem(item)
            }
        }
    }

    override suspend fun deleteTodoItem(id: String, onErrorAction: (() -> Unit)?) {
        runWithSupervisor(onErrorAction) {
            _itemsFlow.update { items ->
                items - items.first { it.id == id }
            }

            databaseDataSource?.deleteItem(id)

            if (isInternetAvailable()) {
                networkDataSource?.deleteItem(id)
            }
        }
    }

    override suspend fun updateTodoItem(item: TodoItem, onErrorAction: (() -> Unit)?) {
        runWithSupervisor(onErrorAction) {
            _itemsFlow.update { items ->
                items.map { if (it.id != item.id) it else item }
            }

            databaseDataSource?.updateItem(item)

            if (isInternetAvailable()) {
                networkDataSource?.updateItem(item)
            }
        }
    }

    override suspend fun getItemByIdOrNull(id: String, onErrorAction: (() -> Unit)?): TodoItem? {
        return _itemsFlow.value.firstOrNull { it.id == id }
    }

    private suspend fun runWithSupervisor(
        onErrorAction: (() -> Unit)?,
        content: suspend () -> Unit
    ) {
        supervisorScope {
            launch(
                Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                    launch(Dispatchers.Main) {
                        onErrorAction?.invoke()
                    }
                }
            ) {
                content()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val ipAddress = InetAddress.getByName("ya.ru")
            ipAddress.hostAddress != null
        } catch (e: Exception) {
            false
        }
    }
}