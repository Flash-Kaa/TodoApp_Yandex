package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemDataSource {
    suspend fun getItems(): Flow<List<TodoItem>>

    suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>>

    suspend fun getItemById(id: String): TodoItem

    suspend fun addItem(item: TodoItem)

    suspend fun updateItem(item: TodoItem)

    suspend fun deleteItem(id: String)
}