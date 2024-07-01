package com.flasshka.data.network.entities

import com.flasshka.domain.entities.TodoItem
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkTodoItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("importance")
    val importance: String,
    @SerializedName("deadline")
    val deadline: Long?,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val created_at: Long,
    @SerializedName("changed_at")
    val changed_at: Long,
    @SerializedName("last_updated_by")
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