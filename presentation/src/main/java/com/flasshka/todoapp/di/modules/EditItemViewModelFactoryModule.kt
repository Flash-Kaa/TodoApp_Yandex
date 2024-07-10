package com.flasshka.todoapp.di.modules

import com.flasshka.domain.usecases.items.AddTodoItemUseCase
import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.todoapp.di.EditItemViewModelScope
import com.flasshka.todoapp.ui.edititem.EditItemVM
import dagger.Module
import dagger.Provides

@Module
internal class EditItemViewModelFactoryModule {
    @Provides
    @EditItemViewModelScope
    fun provideEditItemVMFactory(
        addTodoItem: AddTodoItemUseCase,
        updateTodoItem: UpdateTodoItemUseCase,
        deleteTodoItem: DeleteTodoItemUseCase,
        getTodoItemByIdOrNull: GetTodoItemByIdOrNullUseCase,
        fetchItems: FetchItemsUseCase,
    ): EditItemVM.FactoryWrapperWithUseCases = EditItemVM.FactoryWrapperWithUseCases(
        addTodoItem = addTodoItem,
        updateTodoItem = updateTodoItem,
        deleteTodoItem = deleteTodoItem,
        getTodoItemByIdOrNull = getTodoItemByIdOrNull,
        fetchItems = fetchItems
    )
}