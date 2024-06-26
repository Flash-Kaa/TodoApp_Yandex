package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemsUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val listOfItems = mutableStateListOf<TodoItem>(*getTodoItem.invoke().toTypedArray())

    // DELETE
    fun updateList() {
        val new = getTodoItem.invoke()

        listOfItems.addAll(new.filter { it !in listOfItems })
        listOfItems.removeAll(listOfItems.filter { it !in new })
    }

    fun getItem(id: String): TodoItem? {
        return listOfItems.firstOrNull { it.id == id }
    }

    fun getItems(): List<TodoItem> {
        return listOfItems.filter { it.completed.not() || visibility }
    }

    fun getDoneCount(): Int {
        return listOfItems.count { it.completed }
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
        return {
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    deleteTodoItem.invoke(id)
                }
            }
            // use observe
            val index = listOfItems.indexOfFirst { it.id == id }

            if (index != -1) {
                listOfItems.removeAt(index)
            }
        }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            val item = listOfItems.first {
                it.id == id
            }

            val copy = item.copy(completed = item.completed.not())

            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    updateTodoItem.invoke(copy)
                }
            }
            // use observe
            val index = listOfItems.indexOfFirst { it.id == id }

            if (index != -1) {
                listOfItems[index] = copy
            }
        }
    }
}