package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class TodoItemRepositoryImpl: TodoItemRepository {
    companion object {
        private val db: MutableList<TodoItem> = mutableListOf()
    }
    override fun getTodoItems(): List<TodoItem> {
        return db
    }

    override fun addTodoItem(item: TodoItem) {
        db.add(item)
    }

    override fun deleteTodoItem(id: String) {
        db.firstOrNull { it.id == id }?.let {
            db.remove(it)
        }
    }

    override fun updateTodoItemById(item: TodoItem) {
        val index = db.indexOfFirst {
            item.id == it.id
        }

        db[index] = item
    }
}