package com.flasshka.data.network

import com.flasshka.data.network.entities.BodyItem
import com.flasshka.data.network.entities.ItemWithRevision
import com.flasshka.data.network.entities.ListItemsWithRevision
import com.flasshka.data.network.entities.NetworkTodoItem
import com.flasshka.domain.interfaces.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetworkDataSource(private val service: TodoListService) :
    DataSource<NetworkTodoItem> {
    companion object {
        fun create(): NetworkDataSource {
            return NetworkDataSource(RetrofitBuilder.networkService)
        }
    }

    override suspend fun getItems(): Flow<List<NetworkTodoItem>> {
        val t = service.getItems()
        TodoListService.RuntimeConstants.lastKnownRevision = t.revision

        return flow {
            emit(t.list)
        }
    }

    override suspend fun <R> updateItems(items: R): Flow<List<NetworkTodoItem>> {
        val itemsWithRevision: ListItemsWithRevision =
            service.updateItems(body = RequestUtils.getBody(items))
        TodoListService.RuntimeConstants.lastKnownRevision = itemsWithRevision.revision

        return flow {
            emit(itemsWithRevision.list)
        }
    }

    override suspend fun <R> addItem(item: R): Flow<NetworkTodoItem> {
        val itemWithRevision: ItemWithRevision = service.addItem(body = RequestUtils.getBody(item))
        TodoListService.RuntimeConstants.lastKnownRevision = itemWithRevision.revision

        return flow {
            emit(itemWithRevision.element)
        }
    }

    override suspend fun getItemById(id: String): Flow<NetworkTodoItem> {
        return flow {
            emit(service.getItemById(path = id).element)
        }
    }

    override suspend fun <R> updateItem(item: R): Flow<NetworkTodoItem> {
        if (item !is BodyItem) {
            return flow { }
        }

        val itemWithRevision = service.updateItem(
            path = (item as BodyItem).element.id,
            body = RequestUtils.getBody(item)
        )
        TodoListService.RuntimeConstants.lastKnownRevision = itemWithRevision.revision

        return flow {
            emit(itemWithRevision.element)
        }
    }

    override suspend fun deleteItem(id: String): Flow<NetworkTodoItem> {
        val itemWithRevision = service.deleteItem(path = id)
        TodoListService.RuntimeConstants.lastKnownRevision = itemWithRevision.revision

        return flow {
            emit(itemWithRevision.element)
        }
    }

}