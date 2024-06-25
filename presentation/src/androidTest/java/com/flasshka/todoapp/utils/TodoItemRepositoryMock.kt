package com.flasshka.todoapp.utils

import androidx.compose.runtime.mutableStateListOf
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

internal class TodoItemRepositoryMock : TodoItemRepository {
    private var db = mutableStateListOf<TodoItem>()

    override suspend fun getTodoItems(): List<TodoItem> {
        return db
    }

    override suspend fun addTodoItem(item: TodoItem) {
        db.add(item)
    }

    override suspend fun deleteTodoItem(id: String) {
        val index = db.indexOfFirst { it.id == id }

        if (index != -1) {
            db.removeAt(index)
        }
    }

    override suspend fun updateTodoItemById(item: TodoItem) {
        val index = db.indexOfFirst { it.id == item.id }

        if (index != -1) {
            db[index] = item
        }
    }
}