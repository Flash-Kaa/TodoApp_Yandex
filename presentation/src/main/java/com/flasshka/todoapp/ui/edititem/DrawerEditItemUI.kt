package com.flasshka.todoapp.ui.edititem

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null,
    repository: TodoItemRepository = TodoItemRepositoryImpl()
) {
    val context = LocalContext.current.applicationContext

    val addTodoItem = remember { AddTodoItemUseCase(repository) }
    val updateTodoItem = remember { UpdateTodoItemUseCase(repository) }
    val deleteTodoItem = remember { DeleteTodoItemUseCase(repository) }
    val getTodoItemByIdOrNull = remember { GetTodoItemByIdOrNullUseCase(repository) }

    val editItemVM: EditItemVM = viewModel(
        factory = EditItemVM.Factory(
            router = router,
            itemId = itemId,
            addTodoItem = addTodoItem,
            updateTodoItem = updateTodoItem,
            deleteTodoItem = deleteTodoItem,
            getTodoItemByIdOrNull = getTodoItemByIdOrNull,
            showError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        ),
    )

    EditItemUI(
        state1 = editItemVM.state,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}