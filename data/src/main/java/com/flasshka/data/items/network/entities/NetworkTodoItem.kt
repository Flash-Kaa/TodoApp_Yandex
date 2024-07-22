package com.flasshka.data.items.network.entities

import com.flasshka.domain.entities.TodoItem
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Standard item for work with network
 */
data class NetworkTodoItem(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("importance") val importance: String,
    @SerializedName("deadline") val deadline: Long?,
    @SerializedName("done") val done: Boolean,
    @SerializedName("color") val color: String?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy: String,
    @SerializedName("files") val files: List<String>?
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
                createdAt = created.time,
                changedAt = lastChange?.time ?: (created.time + 10),
                lastUpdatedBy = "12",
                files = files
            )
        }
    }

    fun toItem(): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = TodoItem.Importance.valueOf(importance.replaceFirstChar(Char::uppercaseChar)),
            created = Date(createdAt),
            deadLine = deadline?.let { Date(it) },
            completed = done,
            lastChange = Date(changedAt),
            files = files
        )
    }
}