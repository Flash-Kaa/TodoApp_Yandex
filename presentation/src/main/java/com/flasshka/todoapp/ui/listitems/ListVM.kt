package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
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
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.GetTodoItemFlowUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ListVM(
    private val router: Router,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase,
    private val getTodoItemFlow: GetTodoItemFlowUseCase,
    private val getByIdOrNull: GetTodoItemByIdOrNullUseCase
) : ViewModel() {
    var visibility: Boolean by mutableStateOf(false)
        private set

    fun getItems(): Flow<List<TodoItem>> {
        return getTodoItemFlow().map { list -> list.filter { item -> !item.completed || visibility } }
    }

    fun getDoneCount(): Flow<Int> {
        return getTodoItemFlow().map { list -> list.count { item -> item.completed } }
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
            }
        }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            viewModelScope.launch(Dispatchers.IO) {
                val item = getByIdOrNull(id)

                if (item != null) {
                    val copy = item.copy(completed = item.completed.not())
                    updateTodoItem(copy)
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
            val updateTodoItem = UpdateTodoItemUseCase(repository)
            val deleteTodoItem = DeleteTodoItemUseCase(repository)
            val getTodoItemFlow = GetTodoItemFlowUseCase(repository)
            val getByIdOrNull = GetTodoItemByIdOrNullUseCase(repository)

            return ListVM(
                router = router,
                updateTodoItem = updateTodoItem,
                deleteTodoItem = deleteTodoItem,
                getTodoItemFlow = getTodoItemFlow,
                getByIdOrNull = getByIdOrNull
            ) as T
        }
    }
}