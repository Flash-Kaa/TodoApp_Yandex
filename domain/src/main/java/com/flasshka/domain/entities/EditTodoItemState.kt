package com.flasshka.domain.entities

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class EditTodoItemState(
    val id: String,
    val text: String = "",
    val importance: TodoItem.Importance = TodoItem.Importance.Common,
    val created: Date? = null,
    val deadLine: Long? = null,
    val completed: Boolean = false,
    val lastChange: Date? = null,

    val isUpdate: Boolean = false
) {
    companion object {
        fun getNewState(): EditTodoItemState {
            return EditTodoItemState(UUID.randomUUID().toString())
        }

        fun getNewState(item: TodoItem): EditTodoItemState {
            return item.run {
                EditTodoItemState(
                    id = id,
                    text = text,
                    importance = importance,
                    created = created,
                    deadLine = deadLine?.time,
                    completed = completed,
                    lastChange = lastChange,
                    isUpdate = true
                )
            }
        }
    }

    fun toTodoItem(): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = importance,
            created = created ?: Calendar.getInstance().time,
            deadLine = deadLine?.let { Date(it) },
            completed = completed,
            lastChange = lastChange
        )
    }
}
