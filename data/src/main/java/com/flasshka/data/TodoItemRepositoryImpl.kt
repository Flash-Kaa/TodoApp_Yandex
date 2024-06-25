package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import java.util.Calendar
import java.util.Date
import java.util.UUID
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class TodoItemRepositoryImpl : TodoItemRepository {
    companion object {
        private var list: List<TodoItem> =
            listOf(
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
    }

    private val locker = ReentrantReadWriteLock()

    override suspend fun getTodoItems(): List<TodoItem> {
        return locker.read { list }
    }

    override suspend fun addTodoItem(item: TodoItem) {
        locker.write {
            list += item
        }
    }

    override suspend fun deleteTodoItem(id: String) {
        locker.write {
            list = list.filter { it.id != id }
        }
    }

    override suspend fun updateTodoItemById(item: TodoItem) {
        locker.write {
            list = list.map { if (it.id != item.id) it else item }
        }
    }
}