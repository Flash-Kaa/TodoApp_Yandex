package com.flasshka.todoapp.ui.edititem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.domain.entities.EditTodoItemState
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class EditItemVM(
    itemId: String?,

    private val router: Router,
    private val addTodoItem: AddTodoItemUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase,
    private val getByIdOrNullTodoItem: GetTodoItemByIdOrNullUseCase
) : ViewModel() {
    private var state: EditTodoItemState by mutableStateOf(
        EditTodoItemState.getNewState()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            itemId?.let { id ->
                getByIdOrNullTodoItem(id)?.let { item ->
                    withContext(Dispatchers.Main) {
                        state = EditTodoItemState.getNewState(item)
                    }
                }
            }
        }
    }

    fun getName() = state.text

    fun getImportance() = state.importance

    fun getDeadline() = state.deadLine

    fun getDeleteButtonIsEnabled() = state.isUpdate

    fun getAction(action: EditItemActionType): () -> Unit {
        return when (action) {
            is EditItemActionType.OnSave -> ::onSaveAction
            is EditItemActionType.OnDelete -> ::onDeleteAction
            is EditItemActionType.OnExit -> ::onExitAction
            is EditItemActionType.OnNameChanged -> onNameChanged(action.newValue)
            is EditItemActionType.OnImportanceChanged -> onImportanceChanged(action.newValue)
            is EditItemActionType.OnDeadlineChanged -> onDeadlineChanged(action.newValue)
        }
    }

    private fun onSaveAction() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.isUpdate) {
                val copy = state.copy(lastChange = Calendar.getInstance().time)
                updateTodoItem(copy.toTodoItem())
            } else {
                val copy = state.copy(created = Calendar.getInstance().time)
                addTodoItem(copy.toTodoItem())
            }
        }

        onExitAction()
    }

    private fun onDeleteAction() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTodoItem(state.id)
        }

        onExitAction()
    }

    private fun onExitAction() {
        router.navigateToListOfItems()
    }

    private fun onNameChanged(newValue: String): () -> Unit {
        return { state = state.copy(text = newValue) }
    }

    private fun onImportanceChanged(newValue: TodoItem.Importance): () -> Unit {
        return { state = state.copy(importance = newValue) }
    }

    private fun onDeadlineChanged(newValue: Long?): () -> Unit {
        return { state = state.copy(deadLine = newValue) }
    }

    class Factory(
        private val router: Router,
        private val itemId: String?,
        private val repository: TodoItemRepository = TodoItemRepositoryImpl()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            val addTodoItem = AddTodoItemUseCase(repository)
            val updateTodoItem = UpdateTodoItemUseCase(repository)
            val deleteTodoItem = DeleteTodoItemUseCase(repository)
            val getByIdOrNullTodoItem = GetTodoItemByIdOrNullUseCase(repository)

            return EditItemVM(
                itemId = itemId,
                router = router,
                addTodoItem = addTodoItem,
                updateTodoItem = updateTodoItem,
                deleteTodoItem = deleteTodoItem,
                getByIdOrNullTodoItem = getByIdOrNullTodoItem
            ) as T
        }
    }
}