package com.flasshka.data.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (database != null) {
            return database!!
        }

        database = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            "todo_yandex_database"
        ).build()

        return database!!
    }
}