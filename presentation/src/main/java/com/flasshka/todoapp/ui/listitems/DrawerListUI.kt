package com.flasshka.todoapp.ui.listitems

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.FetchItemsUseCase
import com.flasshka.domain.usecases.GetDoneCountUseCase
import com.flasshka.domain.usecases.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.R
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.snackbarShow

@Composable
fun DrawerListUI(
    router: Router,
    repository: TodoItemRepository
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current.applicationContext

    val listVM: ListVM = viewModel(
        factory = createFactoryForVM(router, repository, context, snackbarHostState)
    )

    val list: List<TodoItem> by listVM.getItems().collectAsState(initial = emptyList())
    val doneCounts: Int by listVM.getDoneCount().collectAsState(initial = 0)

    ListUI(
        snackbarHostState = snackbarHostState,
        doneCount = doneCounts,
        visibilityDoneON = listVM.visibility,
        items = list,
        getAction = listVM::getAction
    )
}

@Composable
private fun createFactoryForVM(
    router: Router,
    repository: TodoItemRepository,
    context: Context,
    snackbarHostState: SnackbarHostState
) = ListVM.Factory(
    router = router,
    updateTodoItem = remember { createUpdateUseCase(repository, context, snackbarHostState) },
    deleteTodoItem = remember { createDeleteUseCase(repository, context, snackbarHostState) },
    getByIdOrNull = remember { createGetByIdUseCase(repository, context, snackbarHostState) },
    getDoneCounts = remember { GetDoneCountUseCase(repository) },
    getItemsWithVisibility = remember { GetItemsWithVisibilityUseCase(repository) },
    fetchItems = remember { createFetchUseCase(repository, context, snackbarHostState) }
)

private fun createFetchUseCase(
    repository: TodoItemRepository,
    context: Context,
    snackbarHostState: SnackbarHostState
) = FetchItemsUseCase(repository) {
    snackbarShow(
        message = context.getString(R.string.fetch_error_message),
        snackbarHostState = snackbarHostState
    )
}

private fun createGetByIdUseCase(
    repository: TodoItemRepository,
    context: Context,
    snackbarHostState: SnackbarHostState
) = GetTodoItemByIdOrNullUseCase(repository) {
    snackbarShow(
        message = context.getString(R.string.get_by_id_error_message),
        snackbarHostState = snackbarHostState
    )
}

private fun createDeleteUseCase(
    repository: TodoItemRepository,
    context: Context,
    snackbarHostState: SnackbarHostState
) = DeleteTodoItemUseCase(repository) {
    snackbarShow(
        message = context.getString(R.string.delete_error_message),
        snackbarHostState = snackbarHostState
    )
}

private fun createUpdateUseCase(
    repository: TodoItemRepository,
    context: Context,
    snackbarHostState: SnackbarHostState
) = UpdateTodoItemUseCase(repository) {
    snackbarShow(
        message = context.getString(R.string.update_error_message),
        snackbarHostState = snackbarHostState
    )
}