package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemsUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router

class FactoryForListVM(
    private val router: Router,
    private val getTodoItem: GetTodoItemsUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListVM(router, getTodoItem, updateTodoItem, deleteTodoItem) as T
    }
}

class ListVM(
    private val router: Router,
    private val getTodoItem: GetTodoItemsUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModel() {
    var visibility: Boolean by mutableStateOf(false)
        private set


    fun getItem(id: String): TodoItem? {
        return getTodoItem.invoke().firstOrNull { it.id == id }
    }

    fun getItems(): List<TodoItem> {
        return getTodoItem.invoke().filter { it.completed.not() || visibility }
    }

    fun getDoneCount(): Int {
        return getTodoItem.invoke().count { it.completed }
    }

    fun getAction(action: ListOfItemsActionType): () -> Unit {
        return when (action) {
            is ListOfItemsActionType.OnCreate -> ::onCreate
            is ListOfItemsActionType.OnChangeItem -> onChangeItem(action.id)
            is ListOfItemsActionType.OnChangeDoneVisibility -> ::onChangeDoneVisibility
            is ListOfItemsActionType.OnDeleteItem -> onDelete(action.id)
            is ListOfItemsActionType.OnChangeDoneItem -> onChangeDoneItem(action.id)
        }
    }

    private fun onCreate() {
        router.navigateToCreateItem()
    }

    private fun onChangeItem(id: String): () -> Unit {
        return { router.navigateToChangeItemById(id) }
    }

    private fun onChangeDoneVisibility() {
        visibility = !visibility
    }

    private fun onDelete(id: String): () -> Unit {
        return { deleteTodoItem.invoke(id) }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            val item = getTodoItem.invoke().first {
                it.id == id
            }

            val copy = item.copy(completed = item.completed.not())

            updateTodoItem.invoke(copy)
        }
    }
}