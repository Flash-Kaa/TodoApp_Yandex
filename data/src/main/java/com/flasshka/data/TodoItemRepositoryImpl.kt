package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.DataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update

class TodoItemRepositoryImpl(
    private val networkDataSource: DataSource? = null,
    private val databaseDataSource: DataSource? = null
) : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems() {
        if (databaseDataSource == null) {
            networkDataSource?.getItems()?.collect { collect ->
                _itemsFlow.update { collect }
            }
        } else if (networkDataSource == null) {
            databaseDataSource.getItems().collect { collect ->
                _itemsFlow.update { collect }
            }
        } else {
            merge(databaseDataSource.getItems(), networkDataSource.getItems())
                .collect { collect -> _itemsFlow.update { it + collect } }
        }
    }

    override suspend fun addTodoItem(item: TodoItem) {
        _itemsFlow.update { items -> items + item }

        databaseDataSource?.addItem(item)
        networkDataSource?.addItem(item)
    }

    override suspend fun deleteTodoItem(id: String) {
        _itemsFlow.update { items ->
            items - items.first { it.id == id }
        }

        databaseDataSource?.deleteItem(id)
        networkDataSource?.deleteItem(id)
    }

    override suspend fun updateTodoItem(item: TodoItem) {
        _itemsFlow.update { items ->
            items.map { if (it.id != item.id) it else item }
        }

        databaseDataSource?.updateItem(item)
        networkDataSource?.updateItem(item)
    }

    override suspend fun getItemByIdOrNull(id: String): TodoItem? {
        return _itemsFlow.value.firstOrNull { it.id == id }
    }
}