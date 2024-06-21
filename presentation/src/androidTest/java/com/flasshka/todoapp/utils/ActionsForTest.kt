package com.flasshka.todoapp.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.actions.ListOfItemsActionType

internal class ActionsForTest(
    private val repository: TodoItemRepository
) {
    var visibility: Boolean by mutableStateOf(false)

    fun invoke(action: ListOfItemsActionType): () -> Unit {
        return when (action) {
            is ListOfItemsActionType.OnCreate -> {
                {}
            }

            is ListOfItemsActionType.OnChangeItem -> {
                {}
            }

            is ListOfItemsActionType.OnChangeDoneVisibility -> {
                { visibility = !visibility }
            }

            is ListOfItemsActionType.OnDeleteItem -> {
                { repository.deleteTodoItem(action.id) }
            }

            is ListOfItemsActionType.OnChangeDoneItem -> {
                {
                    val item = repository.getTodoItems().first {
                        it.id == action.id
                    }

                    val copy = item.copy(completed = item.completed.not())

                    repository.updateTodoItemById(copy)
                }
            }
        }
    }
}