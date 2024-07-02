package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemRepository {
    val itemsFlow: StateFlow<List<TodoItem>>

    suspend fun fetchItems()

    suspend fun addTodoItem(item: TodoItem)

    suspend fun deleteTodoItem(id: String)

    suspend fun updateTodoItem(item: TodoItem)

    suspend fun getItemByIdOrNull(id: String): TodoItem?
}