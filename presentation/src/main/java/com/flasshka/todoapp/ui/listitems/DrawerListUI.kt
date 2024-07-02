package com.flasshka.todoapp.ui.listitems

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.domain.entities.TodoItem
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.FetchItemsUseCase
import com.flasshka.domain.usecases.GetDoneCountUseCase
import com.flasshka.domain.usecases.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerListUI(
    router: Router,
    repository: TodoItemRepository
) {
    val context = LocalContext.current.applicationContext

    val updateTodoItem = remember { UpdateTodoItemUseCase(repository) }
    val deleteTodoItem = remember { DeleteTodoItemUseCase(repository) }
    val getByIdOrNull = remember { GetTodoItemByIdOrNullUseCase(repository) }
    val getDoneCounts = remember { GetDoneCountUseCase(repository) }
    val getItemsWithVisibility = remember { GetItemsWithVisibilityUseCase(repository) }
    val fetchItems = remember { FetchItemsUseCase(repository) }

    val listVM: ListVM = viewModel(
        factory = ListVM.Factory(
            router = router,

            updateTodoItem = updateTodoItem,
            deleteTodoItem = deleteTodoItem,
            getByIdOrNull = getByIdOrNull,
            getDoneCounts = getDoneCounts,
            getItemsWithVisibility = getItemsWithVisibility,
            fetchItems = fetchItems,

            showError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
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