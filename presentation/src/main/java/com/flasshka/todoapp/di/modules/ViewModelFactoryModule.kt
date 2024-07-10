package com.flasshka.todoapp.di.modules

import com.flasshka.domain.usecases.items.AddTodoItemUseCase
import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetDoneCountUseCase
import com.flasshka.domain.usecases.items.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.domain.usecases.token.FetchTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.ui.authorization.AuthorizationVM
import com.flasshka.todoapp.ui.edititem.EditItemVM
import com.flasshka.todoapp.ui.listitems.ListVM
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TokenUseCasesModule::class, ItemsUseCasesModule::class])
internal class ViewModelFactoryModule {
    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideAuthorizationVMFactory(
        fetchToken: FetchTokenUseCase,
        updateToken: UpdateTokenUseCase
    ): AuthorizationVM.FactoryWrapperWithUseCases = AuthorizationVM.FactoryWrapperWithUseCases(
        fetchToken = fetchToken,
        updateToken = updateToken
    )

    @Provides
    @Singleton
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