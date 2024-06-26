package com.flasshka.todoapp.utils

import androidx.compose.runtime.mutableStateListOf
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

internal class TodoItemRepositoryMock : TodoItemRepository {
    private var db = mutableStateListOf<TodoItem>()

    override fun getTodoItems(): List<TodoItem> {
        return db
    }

    override fun addTodoItem(item: TodoItem) {
        db.add(item)
    }

    override fun deleteTodoItem(id: String) {
        val index = db.indexOfFirst { it.id == id }

        if (index != -1) {
            db.removeAt(index)
        }
    }

    override fun updateTodoItemById(item: TodoItem) {
        val index = db.indexOfFirst { it.id == item.id }

        if (index != -1) {
            db[index] = item
        }
    }
}