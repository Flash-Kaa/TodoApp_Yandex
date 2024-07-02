package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemRepository {
    val itemsFlow: StateFlow<List<TodoItem>>

    suspend fun fetchItems(onErrorAction: (() -> Unit)? = null)

    suspend fun addTodoItem(item: TodoItem, onErrorAction: (() -> Unit)? = null)

    suspend fun deleteTodoItem(id: String, onErrorAction: (() -> Unit)? = null)

    suspend fun updateTodoItem(item: TodoItem, onErrorAction: (() -> Unit)? = null)

    suspend fun getItemByIdOrNull(id: String, onErrorAction: (() -> Unit)? = null): TodoItem?
}