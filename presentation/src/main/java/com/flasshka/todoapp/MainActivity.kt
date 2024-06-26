package com.flasshka.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.usecases.AddTodoItemUseCase
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemsUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.edititem.EditItemVM
import com.flasshka.todoapp.ui.edititem.FactoryForEditItemVM
import com.flasshka.todoapp.ui.listitems.FactoryForListVM
import com.flasshka.todoapp.ui.listitems.ListVM
import com.flasshka.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: use DI
        val repository: TodoItemRepository = TodoItemRepositoryImpl()

        val addTodoItem = AddTodoItemUseCase(repository)
        val updateTodoItem = UpdateTodoItemUseCase(repository)
        val deleteTodoItem = DeleteTodoItemUseCase(repository)
        val getTodoItem = GetTodoItemsUseCase(repository)

        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val router = Router(navController)

                    val listVM: ListVM by viewModels<ListVM> {
                        FactoryForListVM(
                            router = router,
                            getTodoItem = getTodoItem,
                            deleteTodoItem = deleteTodoItem,
                            updateTodoItem = updateTodoItem
                        )
                    }

                    val editItemVM: EditItemVM by viewModels<EditItemVM> {
                        FactoryForEditItemVM(
                            router = router,
                            addTodoItem = addTodoItem,
                            updateTodoItem = updateTodoItem,
                            deleteTodoItem = deleteTodoItem
                        )
                    }

                    NavGraph(
                        navController = navController,
                        listVM = listVM,
                        editItemVM = editItemVM
                    )
                }
            }
        }
    }
}