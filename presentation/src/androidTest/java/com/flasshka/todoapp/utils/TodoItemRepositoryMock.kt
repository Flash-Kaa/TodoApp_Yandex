package com.flasshka.todoapp.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

internal class TodoItemRepositoryMock : TodoItemRepository {
    private var db: List<TodoItem> by mutableStateOf(listOf())

    override fun getTodoItems(): List<TodoItem> {
        return db
    }

    override fun addTodoItem(item: TodoItem) {
        db += listOf(item)
    }

    override fun deleteTodoItem(id: String) {
        db = db.filter { it.id != id }
    }

    override fun updateTodoItemById(item: TodoItem) {
        deleteTodoItem(item.id)
        addTodoItem(item)
    }
}