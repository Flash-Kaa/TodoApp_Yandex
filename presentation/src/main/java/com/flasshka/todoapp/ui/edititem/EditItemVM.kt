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
        /* TODO: add or update
        if (upd) {
            changed = now()
            upd()
        } else {
            created = now()
            add()
        }
         */
        onExitAction()
    }

    private fun onDeleteAction() {
        deleteTodoItem.invoke(state.id)
        onExitAction()
    }

    private fun onExitAction() {
        state = EditTodoItemState.getNewState()
        router.getNavigateToListOfItems().invoke()
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