package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemRepository {
    suspend fun fetchItems(): Flow<List<TodoItem>>

    suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>>

    suspend fun addTodoItem(item: TodoItem)

    suspend fun deleteTodoItem(id: String)

    suspend fun updateTodoItem(item: TodoItem)

    suspend fun getItemById(id: String): TodoItem
}