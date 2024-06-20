package com.flasshka.todoapp.ui.listitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flasshka.domain.usecases.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.GetTodoItemsUseCase
import com.flasshka.domain.usecases.UpdateTodoItemUseCase
import com.flasshka.todoapp.navigation.Router

class FactoryForListVM(
    private val router: Router,
    private val getTodoItem: GetTodoItemsUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListVM(router, getTodoItem, updateTodoItem, deleteTodoItem) as T
    }
}

class ListVM(
    private val router: Router,
    private val getTodoItem: GetTodoItemsUseCase,
    private val updateTodoItem: UpdateTodoItemUseCase,
    private val deleteTodoItem: DeleteTodoItemUseCase
) : ViewModel() {

}