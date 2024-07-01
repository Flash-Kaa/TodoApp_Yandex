package com.flasshka.domain.interfaces

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {
    suspend fun getItems(): Flow<List<T>>

    suspend fun <R>updateItems(items: R): Flow<List<T>>

    suspend fun getItemById(id: String): Flow<T>

    suspend fun <R>addItem(item: R): Flow<T>

    suspend fun <R>updateItem(item: R): Flow<T>

    suspend fun deleteItem(id: String): Flow<T>
}