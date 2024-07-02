package com.flasshka.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flasshka.data.database.entities.TodoItemDB

@Database(entities = [TodoItemDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TodoItemsDao
}