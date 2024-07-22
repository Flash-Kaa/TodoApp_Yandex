package com.flasshka.data.items.network

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.items.TodoItemDataSource
import com.flasshka.domain.interfaces.items.TodoItemRepository
import kotlinx.coroutines.flow.Flow

class NetworkRepository(
    private val dataSource: TodoItemDataSource
) : TodoItemRepository {
    override suspend fun fetchItems(): Flow<List<TodoItem>> {
        return dataSource.getItems()
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        return dataSource.updateItems(items)
    }

    override suspend fun addTodoItem(item: TodoItem) {
        dataSource.addItem(item)
    }

    override suspend fun deleteTodoItem(id: String) {
        dataSource.deleteItem(id)
    }

    override suspend fun updateTodoItem(item: TodoItem) {
        dataSource.updateItem(item)
    }

    override suspend fun getItemById(id: String): TodoItem {
        return dataSource.getItemById(id)
    }
}