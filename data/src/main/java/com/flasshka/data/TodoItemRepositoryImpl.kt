package com.flasshka.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import java.util.Calendar
import java.util.Date
import java.util.UUID

class TodoItemRepositoryImpl : TodoItemRepository {
    companion object {
        private var db: List<TodoItem> by mutableStateOf(
            listOf(
                //
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "common item",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "low item",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "urgently item",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "with deadline",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E9.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "with deadline",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E10.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "with deadline",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 4E8.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E9.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time - 1E9.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time - 4E8.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    completed = true
                ),
                //
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "common item with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "low item with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "urgently item with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E9.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E10.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 4E8.toLong())
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time + 1E9.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time - 1E9.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed with deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    deadLine = Date(Calendar.getInstance().time.time - 4E8.toLong()),
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Common,
                    created = Calendar.getInstance().time,
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Low,
                    created = Calendar.getInstance().time,
                    completed = true
                ),
                TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = "completed without deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                    importance = TodoItem.Importance.Urgently,
                    created = Calendar.getInstance().time,
                    completed = true
                )
            )
        )
    }

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