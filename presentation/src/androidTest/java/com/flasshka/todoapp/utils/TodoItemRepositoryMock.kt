
package com.flasshka.todoapp.utils

/**
 * Mock repository
 *//*

internal class TodoItemRepositoryMock : TodoItemRepository {
    private val _itemsFlow: MutableStateFlow<List<TodoItem>> =
        MutableStateFlow(emptyList())
    override val itemsFlow: StateFlow<List<TodoItem>> = _itemsFlow.asStateFlow()

    override suspend fun fetchItems(onErrorAction: (suspend () -> Unit)?) {
        TODO("Not yet implemented")
    }

    override suspend fun addTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        _itemsFlow.update { currentCollection ->
            currentCollection + item
        }
    }

    override suspend fun deleteTodoItem(id: String, onErrorAction: (suspend () -> Unit)?) {
        _itemsFlow.update { currentCollection ->
            currentCollection - currentCollection.first { it.id == id }
        }
    }

    override suspend fun updateTodoItem(item: TodoItem, onErrorAction: (suspend () -> Unit)?) {
        _itemsFlow.update { currentCollection ->
            currentCollection.map { if (it.id != item.id) it else item }
        }
    }

    override suspend fun getItemByIdOrNull(
        id: String,
        onErrorAction: (suspend () -> Unit)?
    ): TodoItem? {
        TODO("Not yet implemented")
    }
}*/
