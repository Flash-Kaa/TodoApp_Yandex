package com.flasshka.todoapp.utils

import androidx.compose.runtime.mutableStateOf
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.actions.ListOfItemsActionType
import kotlinx.coroutines.runBlocking

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

            is ListOfItemsActionType.OnChangeDoneVisibility -> {
                { visibility.value = !visibility.value }
            }

            is ListOfItemsActionType.OnDeleteItem -> {
                {
                    runBlocking {
                        repository.deleteTodoItem(action.id)
                    }
                }
            }

            is ListOfItemsActionType.OnChangeDoneItem -> {
                {
                    runBlocking {
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
}