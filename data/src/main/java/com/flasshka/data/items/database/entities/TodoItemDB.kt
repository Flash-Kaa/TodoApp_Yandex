package com.flasshka.data.items.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flasshka.data.JsonUtils
import com.flasshka.domain.entities.TodoItem
import java.util.Date

/**
 * Items table in db
 */
@Entity
data class TodoItemDB(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("importance") val importance: Int,
    @ColumnInfo("deadline") val deadline: Long?,
    @ColumnInfo("done") val done: Boolean,
    @ColumnInfo("created_at") val created: Long,
    @ColumnInfo("changed_at") val lastChange: Long?,
    @ColumnInfo("files") val files: String? = null
) {
    companion object {
        fun TodoItem.toItemDB(): TodoItemDB {
            return TodoItemDB(
                id = id,
                text = text,
                importance = TodoItem.Importance.entries.indexOf(importance),
                deadline = deadLine?.time,
                done = completed,
                created = created.time,
                lastChange = lastChange?.time,
                files = JsonUtils.convertToJson(files)
            )
        }
    }

    fun toItem(): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = TodoItem.Importance.entries.get(importance),
            created = Date(created),
            deadLine = deadline?.let { Date(it) },
            completed = done,
            lastChange = lastChange?.let { Date(it) },
            files = files?.let { JsonUtils.convertFromJson(files, emptyList<String>().javaClass) }
        )
    }
}