package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository

class TodoItemRepositoryImpl: TodoItemRepository {
    private val db: MutableList<TodoItem> = mutableListOf()

    override fun getTodoItems(): List<TodoItem> {
        return db
    }

    override fun addTodoItem(item: TodoItem) {
        db.add(item)
    }

    override fun removeTodoItem(item: TodoItem) {
        db.remove(item)
    }

    override fun changeTodoItemById(item: TodoItem) {
        val index = db.indexOfFirst {
            item.id == it.id
        }

        db[index] = item
    }
}