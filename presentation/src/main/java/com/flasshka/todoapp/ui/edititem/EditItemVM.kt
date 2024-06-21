package com.flasshka.todoapp.ui.edititem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flasshka.domain.entities.EditTodoItemState
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.navigation.Router
import java.util.Calendar

class FactoryForEditItemVM(
    private val router: Router,
    private val addTodoItem: AddTodoItemUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditItemVM(router, addTodoItem, updateTodoItem, deleteTodoItem) as T
    }
}

class EditItemVM(
    private val router: Router,
    private val addTodoItem: AddTodoItemUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModel() {
    private var state: EditTodoItemState by mutableStateOf(
        EditTodoItemState.getNewState()
    )

    fun updateState(item: TodoItem? = null) {
        state = if (item == null) {
            EditTodoItemState.getNewState()
        } else {
            EditTodoItemState.getNewState(item)
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
            is EditItemActionType.OnNameChanged -> onNameChanged(action.newVale)
            is EditItemActionType.OnImportanceChanged -> onImportanceChanged(action.newValue)
            is EditItemActionType.OnDeadlineChanged -> onDeadlineChanged(action.newValue)
        }
    }

    private fun onSaveAction() {
        if (state.isUpdate) {
            val copy = state.copy(lastChange = Calendar.getInstance().time)
            updateTodoItem.invoke(copy.toTodoItem())
        } else {
            val copy = state.copy(created = Calendar.getInstance().time)
            addTodoItem.invoke(copy.toTodoItem())
        }

        onExitAction()
    }

    private fun onDeleteAction() {
        deleteTodoItem.invoke(state.id)
        onExitAction()
    }

    private fun onExitAction() {
        state = EditTodoItemState.getNewState()
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
}