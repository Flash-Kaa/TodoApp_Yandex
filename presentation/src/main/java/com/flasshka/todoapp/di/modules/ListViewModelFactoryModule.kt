package com.flasshka.todoapp.di.modules

import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetDoneCountUseCase
import com.flasshka.domain.usecases.items.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.todoapp.di.ListViewModelScope
import com.flasshka.todoapp.ui.listitems.ListVM
import dagger.Module
import dagger.Provides

@Module
internal class ListViewModelFactoryModule {
    @Provides
    @ListViewModelScope
    fun provideListVMFactory(
        updateTodoItem: UpdateTodoItemUseCase,
        deleteTodoItem: DeleteTodoItemUseCase,
        getByIdOrNull: GetTodoItemByIdOrNullUseCase,
        getDoneCounts: GetDoneCountUseCase,
        getItemsWithVisibility: GetItemsWithVisibilityUseCase,
        fetchItems: FetchItemsUseCase
    ): ListVM.FactoryWrapperWithUseCases = ListVM.FactoryWrapperWithUseCases(
        updateTodoItem = updateTodoItem,
        deleteTodoItem = deleteTodoItem,
        getByIdOrNull = getByIdOrNull,
        getDoneCounts = getDoneCounts,
        getItemsWithVisibility = getItemsWithVisibility,
        fetchItems = fetchItems
    )
}