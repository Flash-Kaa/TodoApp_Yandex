package com.flasshka.data.database

import com.flasshka.data.database.entities.TodoItemDB.Companion.toItemDB
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Data Source impl for data in DB
 */
internal class DataSourceDB(private val dao: TodoItemsDao) : TodoItemDataSource {
    private val _items: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())

    override suspend fun getItems(): Flow<List<TodoItem>> {
        val items = dao.getItems()
        _items.update { items.map { it.toItem() } }
        return _items
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        for (item in items) {
            if (_items.value.any { it.id == item.id }) {
                updateItem(item)
            } else {
                addItem(item)
            }
        }

        return _items
    }

    override suspend fun getItemById(id: String): TodoItem {
        return dao.getItemById(id).toItem()
    }

    override suspend fun addItem(item: TodoItem) {
        _items.update { items -> items + item }
        dao.addItem(item.toItemDB())
    }

    override suspend fun updateItem(item: TodoItem) {
        _items.update { items -> updateItemById(items, item) }

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
        _items.update { items -> items.filter { it.id != id } }
        dao.deleteItem(id)
    }

    private fun updateItemById(
        items: List<TodoItem>,
        item: TodoItem
    ) = items.map { if (it.id != item.id) it else item }
}