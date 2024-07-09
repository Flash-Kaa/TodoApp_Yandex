package com.flasshka.data.network.entities

import com.flasshka.domain.entities.TodoItem
import java.util.Date

/**
 * Standard item for work with network
 */
data class NetworkTodoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
) {
    companion object {
        fun TodoItem.toNetwork(): NetworkTodoItem {
            return NetworkTodoItem(
                id = id,
                text = text,
                importance = importance.name.toLowerCase(),
                deadline = deadLine?.time,
                done = completed,
                color = "#FFFFFF",
                created_at = created.time,
                changed_at = lastChange?.time ?: (created.time + 10),
                last_updated_by = "12"
            )
        }
    }

    fun toItem(): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = TodoItem.Importance.valueOf(importance.replaceFirstChar(Char::uppercaseChar)),
            created = Date(created_at),
            deadLine = deadline?.let { Date(it) },
            completed = done,
            lastChange = Date(changed_at)
        )
    }
}