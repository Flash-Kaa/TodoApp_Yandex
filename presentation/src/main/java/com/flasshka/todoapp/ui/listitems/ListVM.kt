package com.flasshka.todoapp.ui.listitems

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetDoneCountUseCase
import com.flasshka.domain.usecases.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ListVM(
    private val router: Router,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase,
    private val getByIdOrNull: GetTodoItemByIdOrNullUseCase,
    private val getDoneCounts: GetDoneCountUseCase,
    private val getItemsWithVisibility: GetItemsWithVisibilityUseCase,

    private val showError: ((String) -> Unit)? = null
) : ViewModel() {
    var visibility: Boolean by mutableStateOf(false)
        private set

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
            viewModelScope.launch(
                Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                    showError?.invoke("Не можем удалить")
                }
            ) {
                deleteTodoItem(id)
            }
        }
    }

    private fun onChangeDoneItem(id: String): () -> Unit {
        return {
            viewModelScope.launch(
                Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                    showError?.invoke("Не получилось открыть меню изменения")
                }
            ) {
                getByIdOrNull(id)?.let {
                    val copy = it.copy(completed = it.completed.not())
                    updateTodoItem(copy)
                }
            }
        }
    }

    class Factory(
        private val router: Router,

        private val updateTodoItem: UpdateTodoItemUseCase,
        private val deleteTodoItem: DeleteTodoItemUseCase,
        private val getByIdOrNull: GetTodoItemByIdOrNullUseCase,
        private val getDoneCounts: GetDoneCountUseCase,
        private val getItemsWithVisibility: GetItemsWithVisibilityUseCase,

        private val showError: ((String) -> Unit)? = null
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            return ListVM(
                router = router,
                updateTodoItem = updateTodoItem,
                deleteTodoItem = deleteTodoItem,
                getByIdOrNull = getByIdOrNull,
                getDoneCounts = getDoneCounts,
                getItemsWithVisibility = getItemsWithVisibility,
                showError = showError
            ) as T
        }
    }
}