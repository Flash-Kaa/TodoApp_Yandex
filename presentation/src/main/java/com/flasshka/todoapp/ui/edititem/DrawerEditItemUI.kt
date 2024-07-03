package com.flasshka.todoapp.ui.edititem

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.FetchItemsUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.R
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.snackbarShow

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null,
    repository: TodoItemRepository
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current.applicationContext

    val addTodoItem = remember { AddTodoItemUseCase(repository) }
    val updateTodoItem = remember { UpdateTodoItemUseCase(repository) }
    val deleteTodoItem = remember { DeleteTodoItemUseCase(repository) }

    val getTodoItemByIdOrNull = remember {
        GetTodoItemByIdOrNullUseCase(repository) {
            snackbarShow(
                message = context.getString(R.string.get_by_id_error_message),
                snackbarHostState = snackbarHostState
            )
        }
    }

    val fetchItems = remember {
        FetchItemsUseCase(repository) {
            snackbarShow(
                message = context.getString(R.string.fetch_error_message),
                snackbarHostState = snackbarHostState,
                actionLabel = "retry",
                onActionPerformed = { FetchItemsUseCase(repository).invoke() }
            )
        }
    }

    val viewModel: EditItemVM = viewModel(
        factory = EditItemVM.Factory(
            router = router,
            itemId = itemId,
            addTodoItem = addTodoItem,
            updateTodoItem = updateTodoItem,
            deleteTodoItem = deleteTodoItem,
            getTodoItemByIdOrNull = getTodoItemByIdOrNull,
            fetchItems = fetchItems
        ),
    )

    val state: EditTodoItemState by viewModel.state.collectAsState(EditTodoItemState.getNewState())

    EditItemUI(
        snackbarHostState = snackbarHostState,
        state = state,
        deleteButtonIsEnabled = viewModel::getDeleteButtonIsEnabled,
        getAction = viewModel::getAction,
    )
}