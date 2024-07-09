package com.flasshka.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.flasshka.data.database.entities.TodoItemDB

@Dao
interface TodoItemsDao {
    @Query("SELECT * FROM TodoItemDB")
    fun getItems(): List<TodoItemDB>

    @Query("SELECT * FROM TodoItemDB WHERE id = :id")
    fun getItemById(id: String): TodoItemDB

    @Insert
    fun addItem(item: TodoItemDB)

    @Query(
        "UPDATE TodoItemDB SET " +
                "text = :text, importance = :importance, " +
                "deadline = :deadline, done = :done, " +
                "created_at = :created, changed_at = :lastChange " +
                "WHERE id = :id"
    )
    fun updateItem(
        id: String,
        text: String,
        importance: Int,
        created: Long,
        deadline: Long?,
        done: Boolean,
        lastChange: Long?
    )

    @Query("DELETE FROM TodoItemDB WHERE id = :id")
    fun deleteItem(id: String)
}