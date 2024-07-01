package com.flasshka.todoapp.ui.edititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.todoapp.ui.EditTodoItemState
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.FetchItemsUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class EditItemVM(
    itemId: String?,

    private val router: Router,

    private val addTodoItem: AddTodoItemUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase,
    private val getTodoItemByIdOrNull: GetTodoItemByIdOrNullUseCase,
    private val fetchItems: FetchItemsUseCase,

    private val showError: ((String) -> Unit)? = null,
) : ViewModel() {
    private val _state: MutableStateFlow<EditTodoItemState> =
        MutableStateFlow(EditTodoItemState.getNewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(
            Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                showError?.invoke("Не получается обновить список")
            }
        ) {
            fetchItems()
        }

        viewModelScope.launch(
            Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                viewModelScope.launch(Dispatchers.Main) {
                    showError?.invoke("Не удаётся создать или редактировать объект")
                }
            }
        ) {
            itemId?.let { id ->
                getTodoItemByIdOrNull(id)?.let { item ->
                    _state.update { EditTodoItemState.getNewState(item) }
                }
            }
        }
    }

    fun getDeleteButtonIsEnabled() = _state.value.isUpdate

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
        viewModelScope.launch(
            Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                viewModelScope.launch(Dispatchers.Main) {
                    showError?.invoke("Не получилось сохранить изменения")
                }
            }
        ) {
            if (_state.value.isUpdate) {
                val copy = _state.value.copy(lastChange = Calendar.getInstance().time)
                updateTodoItem(copy.toTodoItem())
            } else {
                val copy = _state.value.copy(created = Calendar.getInstance().time)
                addTodoItem(copy.toTodoItem())
            }
        }

        onExitAction()
    }

    private fun onDeleteAction() {
        viewModelScope.launch(
            Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
                viewModelScope.launch(Dispatchers.Main) {
                    showError?.invoke("Не можем удалить")
                }
            }
        ) {
            deleteTodoItem(_state.value.id)
        }

        onExitAction()
    }

    private fun onExitAction() {
        router.navigateToListOfItems()
    }

    private fun onNameChanged(newValue: String): () -> Unit {
        return { _state.update { it.copy(text = newValue) } }
    }

    private fun onImportanceChanged(newValue: TodoItem.Importance): () -> Unit {
        return { _state.update { it.copy(importance = newValue) } }
    }

    private fun onDeadlineChanged(newValue: Long?): () -> Unit {
        return { _state.update { it.copy(deadLine = newValue) } }
    }

    class Factory(
        private val router: Router,
        private val itemId: String?,

        private val addTodoItem: AddTodoItemUseCase,
        private val updateTodoItem: UpdateTodoItemUseCase,
        private val deleteTodoItem: DeleteTodoItemUseCase,
        private val getTodoItemByIdOrNull: GetTodoItemByIdOrNullUseCase,
        private val fetchItems: FetchItemsUseCase,

        private val showError: ((String) -> Unit)? = null,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditItemVM(
                itemId = itemId,
                router = router,
                addTodoItem = addTodoItem,
                updateTodoItem = updateTodoItem,
                deleteTodoItem = deleteTodoItem,
                getTodoItemByIdOrNull = getTodoItemByIdOrNull,
                fetchItems = fetchItems,
                showError = showError
            ) as T
        }
    }
}