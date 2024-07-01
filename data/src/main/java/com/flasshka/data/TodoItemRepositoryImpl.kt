package com.flasshka.data

import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date
import java.util.UUID
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class TodoItemRepositoryImpl : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    private val locker = ReentrantReadWriteLock()

    override suspend fun fetchItems() {
        _itemsFlow.update { TemporaryData.list }

        return locker.read {
            TemporaryData.list
        }
    }

    override suspend fun addTodoItem(item: TodoItem) {
        _itemsFlow.update { items -> items + item }

        locker.write {
            TemporaryData.list += item
        }
    }

    override suspend fun deleteTodoItem(id: String) {
        _itemsFlow.update { currentCollection ->
            currentCollection - currentCollection.first { it.id == id }
        }

        locker.write {
            TemporaryData.list -= TemporaryData.list.first { it.id == id }
        }
    }

    override suspend fun updateTodoItemById(item: TodoItem) {
        locker.write {
            _itemsFlow.update { currentCollection ->
                currentCollection.map { if (it.id != item.id) it else item }
            }

            TemporaryData.list = TemporaryData.list.map { if (it.id != item.id) it else item }
        }
    }

    override suspend fun getItemByIdOrNull(id: String): TodoItem? {
        TODO("Not yet implemented")
    }

    private object TemporaryData {
        var list = listOf(
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "common item",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "with deadline",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time,
                deadLine = Date(Calendar.getInstance().time.time + 4E8.toLong())
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "completed with deadline",
                importance = TodoItem.Importance.Important,
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
                importance = TodoItem.Importance.Basic,
                created = Calendar.getInstance().time,
                deadLine = Date(Calendar.getInstance().time.time - 4E8.toLong()),
                completed = true
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "completed without deadline",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time,
                completed = true
            ),
            //
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "common item with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time,
                deadLine = Date(Calendar.getInstance().time.time + 4E8.toLong())
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "completed with deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                importance = TodoItem.Importance.Important,
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
                importance = TodoItem.Importance.Basic,
                created = Calendar.getInstance().time,
                deadLine = Date(Calendar.getInstance().time.time - 4E8.toLong()),
                completed = true
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "completed without deadline with long message: with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message, with_long_message",
                importance = TodoItem.Importance.Basic,
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
                importance = TodoItem.Importance.Important,
                created = Calendar.getInstance().time,
                completed = true
            )
        )
    }
}