package com.flasshka.data.network

import com.flasshka.data.network.entities.BodyItem
import com.flasshka.data.network.entities.NetworkTodoItem
import com.flasshka.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.DataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update

class NetworkRepository(private val dataSource: DataSource<NetworkTodoItem>) : TodoItemRepository {
    companion object {
        fun create(): NetworkRepository {
            return NetworkRepository(NetworkDataSource.create())
        }
    }

    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems() {
        dataSource.getItems().collect { collect ->
            val list = collect.map { it.toItem() }
            _itemsFlow.update { list }
        }
    }

    override suspend fun addTodoItem(item: TodoItem) {
        _itemsFlow.update { it + item }

        dataSource.addItem(BodyItem(element = item.toNetwork()))
    }

    override suspend fun deleteTodoItem(id: String) {
        _itemsFlow.update { it.filter { it.id != id } }

        dataSource.deleteItem(id)
    }

    override suspend fun updateTodoItemById(item: TodoItem) {
        _itemsFlow.update {
            it.map { element -> if (element.id == item.id) item else element }
        }

        val body = BodyItem(element = item.toNetwork())
        dataSource.updateItem(body)
    }

    override suspend fun getItemByIdOrNull(id: String): TodoItem? {
        return try {
            dataSource.getItemById(id).last().toItem()
        } catch (e: Exception) {
            null
        }
    }
}