package com.flasshka.data.database

import android.content.Context
import com.flasshka.data.database.entities.TodoItemDB.Companion.toItemDB
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataSourceDB(private val dao: TodoItemsDao) : DataSource {
    companion object {
        fun create(context: Context): DataSourceDB {
            val dao = DatabaseBuilder.getDatabase(context).getDao()
            return DataSourceDB(dao)
        }
    }

    override suspend fun getItems(): Flow<List<TodoItem>> {
        return flow { emit(dao.getItems().map { it.toItem() }) }
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        for(i in items) {
            updateItem(i)
        }

        return flow { emit(items) }
    }

    override suspend fun getItemById(id: String): TodoItem {
        return dao.getItemById(id).toItem()
    }

    override suspend fun addItem(item: TodoItem) {
        dao.addItem(item.toItemDB())
    }

    override suspend fun updateItem(item: TodoItem) {
        with(item.toItemDB()) {
            dao.updateItem(
                id = id,
                text = text,
                importance = importance,
                deadline = deadline,
                done = done,
                created = created,
                lastChange = lastChange
            )
        }
    }

    override suspend fun deleteItem(id: String) {
        dao.deleteItem(id)
    }
}