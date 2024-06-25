package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemsUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListVM(
    private val router: Router,
    private val getTodoItems: GetTodoItemsUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModel() {
    var visibility: Boolean by mutableStateOf(false)
        private set

    private val listOfItems = mutableStateListOf<TodoItem>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getTodoItems()

            withContext(Dispatchers.Main) {
                listOfItems.addAll(items)
            }
        }
    }

    fun getItems(): List<TodoItem> {
        return listOfItems.filter { !it.completed || visibility }
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
            viewModelScope.launch(Dispatchers.IO) {
                deleteTodoItem(id)

                val index = listOfItems.indexOfFirst { it.id == id }

                if (index != -1) {
                    withContext(Dispatchers.Main) {
                        listOfItems.removeAt(index)
                    }
                }
            }
        }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            viewModelScope.launch(Dispatchers.IO) {
                val item = listOfItems.first { it.id == id }
                val copy = item.copy(completed = item.completed.not())

                updateTodoItem(copy)

                val index = listOfItems.indexOfFirst { it.id == id }

                if (index != -1) {
                    withContext(Dispatchers.Main) {
                        listOfItems[index] = copy
                    }
                }
            }
        }
    }

    class Factory(
        private val router: Router,
        private val repository: TodoItemRepository = TodoItemRepositoryImpl()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val getTodoItems = GetTodoItemsUseCase(repository)
            val updateTodoItem = UpdateTodoItemUseCase(repository)
            val deleteTodoItem = DeleteTodoItemUseCase(repository)

            return ListVM(
                router = router,
                getTodoItems = getTodoItems,
                updateTodoItem = updateTodoItem,
                deleteTodoItem = deleteTodoItem
            ) as T
        }
    }
}