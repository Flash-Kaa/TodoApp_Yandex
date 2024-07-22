package com.flasshka.data.items.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flasshka.data.items.database.entities.TodoItemDB

/**
 * DB for application
 */
@Database(entities = [TodoItemDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TodoItemsDao
}