package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem

interface TodoItemRepository {
    fun getTodoItems(): List<TodoItem>

    fun addTodoItem(item: TodoItem)

    fun deleteTodoItem(id: String)

    fun updateTodoItemById(item: TodoItem)
}