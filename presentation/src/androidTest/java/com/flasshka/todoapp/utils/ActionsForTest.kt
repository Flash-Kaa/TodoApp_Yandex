package com.flasshka.todoapp.utils

import androidx.compose.runtime.mutableStateOf
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.actions.ListOfItemsActionType
import kotlinx.coroutines.runBlocking

/**
 * Mock store actions for the test list UI
 */
internal class ActionsForTest(
    private val repository: TodoItemRepository
) {
    val visibility = mutableStateOf(false)

    fun invoke(action: ListOfItemsActionType): () -> Unit {
        return when (action) {
            is ListOfItemsActionType.OnCreate -> {
                {}
            }

            is ListOfItemsActionType.OnChangeItem -> {
                {}
            }

            is ListOfItemsActionType.OnChangeDoneVisibility -> ::onChangeVisibility
            is ListOfItemsActionType.OnDeleteItem -> onDelete(action)
            is ListOfItemsActionType.OnChangeDoneItem -> onChangeDone(action)
        }
    }

    private fun onChangeVisibility(): () -> Unit {
        return { visibility.value = !visibility.value }
    }

    private fun onDelete(action: ListOfItemsActionType.OnDeleteItem): () -> Unit {
        return {
            runBlocking {
                repository.deleteTodoItem(action.id)
            }
        }
    }

    private fun onChangeDone(action: ListOfItemsActionType.OnChangeDoneItem): () -> Unit {
        return {
            runBlocking {
                val item = getFirstWithId(action)
                val copy = item.copy(completed = item.completed.not())

                repository.updateTodoItem(copy)
            }
        }
    }

    private fun getFirstWithId(action: ListOfItemsActionType.OnChangeDoneItem): TodoItem {
        return repository.itemsFlow.value.first {
            it.id == action.id
        }
    }
}