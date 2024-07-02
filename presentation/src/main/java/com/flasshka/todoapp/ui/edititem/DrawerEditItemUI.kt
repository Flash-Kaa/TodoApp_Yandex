package com.flasshka.todoapp.ui.edititem

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
import com.flasshka.todoapp.ui.showToast

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null,
    repository: TodoItemRepository
) {
    val context = LocalContext.current.applicationContext

    val addTodoItem = remember {
        AddTodoItemUseCase(repository) {
            showToast(context, context.getString(R.string.add_error_message))
        }
    }
    val updateTodoItem = remember {
        UpdateTodoItemUseCase(repository) {
            showToast(context, context.getString(R.string.update_error_message))
        }
    }
    val deleteTodoItem = remember {
        DeleteTodoItemUseCase(repository) {
            showToast(context, context.getString(R.string.delete_error_message))
        }
    }
    val getTodoItemByIdOrNull = remember {
        GetTodoItemByIdOrNullUseCase(repository) {
            showToast(context, context.getString(R.string.get_by_id_error_message))
        }
    }
    val fetchItems = remember {
        FetchItemsUseCase(repository) {
            showToast(context, context.getString(R.string.fetch_error_message))
        }
    }


    val editItemVM: EditItemVM = viewModel(
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

    val state: EditTodoItemState by editItemVM.state.collectAsState(initial = EditTodoItemState.getNewState())

    EditItemUI(
        state = state,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}