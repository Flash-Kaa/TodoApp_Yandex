package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.TodoItem

interface TodoItemRepository {
    fun getTodoItems(): List<TodoItem>

    fun addTodoItem(item: TodoItem)

    fun removeTodoItem(item: TodoItem)

    fun changeTodoItemById(item: TodoItem)
}