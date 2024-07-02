package com.flasshka.todoapp.ui.listitems

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
import com.flasshka.todoapp.ui.showToast

@Composable
fun DrawerListUI(
    router: Router,
    repository: TodoItemRepository
) {
    val context = LocalContext.current.applicationContext

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
    val getByIdOrNull = remember {
        GetTodoItemByIdOrNullUseCase(repository) {
            showToast(context, context.getString(R.string.get_by_id_error_message))
        }
    }
    val getDoneCounts = remember { GetDoneCountUseCase(repository) }
    val getItemsWithVisibility = remember { GetItemsWithVisibilityUseCase(repository) }
    val fetchItems = remember {
        FetchItemsUseCase(repository) {
            showToast(context, context.getString(R.string.fetch_error_message))
        }
    }

    val listVM: ListVM = viewModel(
        factory = ListVM.Factory(
            router = router,

            updateTodoItem = updateTodoItem,
            deleteTodoItem = deleteTodoItem,
            getByIdOrNull = getByIdOrNull,
            getDoneCounts = getDoneCounts,
            getItemsWithVisibility = getItemsWithVisibility,
            fetchItems = fetchItems
        )
    )

    val list: List<TodoItem> by listVM.getItems().collectAsState(initial = emptyList())
    val doneCounts: Int by listVM.getDoneCount().collectAsState(initial = 0)

    ListUI(
        doneCount = doneCounts,
        visibilityDoneON = listVM.visibility,
        items = list,
        getAction = listVM::getAction
    )
}