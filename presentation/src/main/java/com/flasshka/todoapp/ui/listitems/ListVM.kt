package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetDoneCountUseCase
import com.flasshka.domain.usecases.items.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 *  Managing the status and actions related to the task list in the UI
 */
internal class ListVM(
    private val router: Router,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase,
    private val getByIdOrNull: GetTodoItemByIdOrNullUseCase,
    private val getDoneCounts: GetDoneCountUseCase,
    private val getItemsWithVisibility: GetItemsWithVisibilityUseCase,
    private val fetchItems: FetchItemsUseCase
) : ViewModel() {
    var visibility: Boolean by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            fetchItems()
        }
    }

    fun getItems(): Flow<List<TodoItem>> {
        return getItemsWithVisibility(visibility)
    }

    fun getDoneCount(): Flow<Int> {
        return getDoneCounts()
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
                deleteTodoItem(id)
            }
        }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            viewModelScope.launch {
                updateDone(id)
            }
        }
    }

    private suspend fun ListVM.updateDone(id: String) {
        getByIdOrNull(id)?.let {
            val copy = it.copy(completed = it.completed.not())
            updateTodoItem(copy)
        }
    }

    /**
     * Creates a ListVM without @AssistedInject
     */
    class FactoryWrapperWithUseCases(
        private val updateTodoItem: UpdateTodoItemUseCase,
        private val deleteTodoItem: DeleteTodoItemUseCase,
        private val getByIdOrNull: GetTodoItemByIdOrNullUseCase,
        private val getDoneCounts: GetDoneCountUseCase,
        private val getItemsWithVisibility: GetItemsWithVisibilityUseCase,
        private val fetchItems: FetchItemsUseCase
    ) {
        inner class InnerFactory(
            private val router: Router
        ): ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListVM(
                    router = router,
                    updateTodoItem = updateTodoItem,
                    deleteTodoItem = deleteTodoItem,
                    getByIdOrNull = getByIdOrNull,
                    getDoneCounts = getDoneCounts,
                    getItemsWithVisibility = getItemsWithVisibility,
                    fetchItems = fetchItems
                ) as T
            }
        }
    }
}