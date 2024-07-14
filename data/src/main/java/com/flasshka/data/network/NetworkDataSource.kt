package com.flasshka.data.network

import com.flasshka.data.network.entities.BodyItem
import com.flasshka.data.network.entities.BodyListItems
import com.flasshka.data.network.entities.ItemWithRevision
import com.flasshka.data.network.entities.ListItemsWithRevision
import com.flasshka.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.TodoItemDataSource
import com.flasshka.domain.interfaces.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Data Source impl for data in network
 */
internal class NetworkDataSource(
    private val service: TodoListService,
    private val tokenRepository: TokenRepository
) : TodoItemDataSource {
    private val _items: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val itemsFlow: StateFlow<List<TodoItem>> = _items.asStateFlow()

    override suspend fun getItems(): Flow<List<TodoItem>> {
        val items = service.getItems()

        val revision = Token.Revision(items.revision)
        tokenRepository.updateRevision(revision = revision)

        _items.update { items.list.map { it.toItem() } }

        return itemsFlow
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        val body = BodyListItems(list = items.map { it.toNetwork() })
        val itemsWithRevision: ListItemsWithRevision =
            service.updateItems(body = body)

        val revision = Token.Revision(itemsWithRevision.revision)
        tokenRepository.updateRevision(revision = revision)

        _items.update { itemsWithRevision.list.map { it.toItem() } }

        return itemsFlow
    }

    override suspend fun addItem(item: TodoItem) {
        _items.update { it + item }
        val body = BodyItem(element = item.toNetwork())
        val itemWithRevision: ItemWithRevision = service.addItem(body = body)

        val revision = Token.Revision(itemWithRevision.revision)
        tokenRepository.updateRevision(revision = revision)
    }

    override suspend fun getItemById(id: String): TodoItem {
        return service.getItemById(path = id).element.toItem()
    }

    override suspend fun updateItem(item: TodoItem) {
        _items.update { items -> updateItemById(items, item) }
        val body = BodyItem(element = item.toNetwork())

        val itemWithRevision = service.updateItem(
            path = body.element.id,
            body = body
        )

        val revision = Token.Revision(itemWithRevision.revision)
        tokenRepository.updateRevision(revision = revision)
    }

    override suspend fun deleteItem(id: String) {
        _items.update { items -> items.filter { it.id != id } }

        val itemWithRevision = service.deleteItem(path = id)

        val revision = Token.Revision(itemWithRevision.revision)
        tokenRepository.updateRevision(revision = revision)
    }

    private fun updateItemById(
        items: List<TodoItem>,
        item: TodoItem
    ) = items.map { if (it.id != item.id) it else item }
}