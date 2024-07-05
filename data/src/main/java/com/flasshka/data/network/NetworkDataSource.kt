package com.flasshka.data.network

import com.flasshka.data.network.entities.BodyItem
import com.flasshka.data.network.entities.BodyListItems
import com.flasshka.data.network.entities.ItemWithRevision
import com.flasshka.data.network.entities.ListItemsWithRevision
import com.flasshka.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Data Source impl for data in network
 */
class NetworkDataSource @Inject constructor(private val service: TodoListService) :
    TodoItemDataSource {
    override suspend fun getItems(): Flow<List<TodoItem>> {
        val items = service.getItems()
        ServiceConstants.lastKnownRevision = items.revision

        return flow {
            emit(items.list.map { it.toItem() })
        }
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        val body = BodyListItems(list = items.map { it.toNetwork() })


        val itemsWithRevision: ListItemsWithRevision =
            service.updateItems(body = RequestUtils.getBody(body))
        ServiceConstants.lastKnownRevision = itemsWithRevision.revision

        return flow {
            emit(itemsWithRevision.list.map { it.toItem() })
        }
    }

    override suspend fun addItem(item: TodoItem) {
        val body = BodyItem(element = item.toNetwork())

        val itemWithRevision: ItemWithRevision = service.addItem(body = RequestUtils.getBody(body))
        ServiceConstants.lastKnownRevision = itemWithRevision.revision
    }

    override suspend fun getItemById(id: String): TodoItem {
        return service.getItemById(path = id).element.toItem()
    }

    override suspend fun updateItem(item: TodoItem) {
        val body = BodyItem(element = item.toNetwork())

        val itemWithRevision = service.updateItem(
            path = body.element.id,
            body = RequestUtils.getBody(body)
        )

        ServiceConstants.lastKnownRevision = itemWithRevision.revision
    }

    override suspend fun deleteItem(id: String) {
        val itemWithRevision = service.deleteItem(path = id)
        ServiceConstants.lastKnownRevision = itemWithRevision.revision
    }
}