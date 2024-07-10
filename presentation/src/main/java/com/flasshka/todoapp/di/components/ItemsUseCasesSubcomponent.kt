package com.flasshka.todoapp.di.components

import com.flasshka.domain.usecases.items.AddTodoItemUseCase
import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetDoneCountUseCase
import com.flasshka.domain.usecases.items.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.todoapp.di.ItemsUseCaseScope
import com.flasshka.todoapp.di.modules.ItemsUseCasesModule
import dagger.Subcomponent

@ItemsUseCaseScope
@Subcomponent(modules = [ItemsUseCasesModule::class])
internal interface ItemsUseCasesSubcomponent {
    fun editItemVMComponent(): EditItemViewModelSubcomponent

    fun listVMComponent(): ListViewModelSubcomponent

    fun fetchUseCase(): FetchItemsUseCase

    fun updateUseCase(): UpdateTodoItemUseCase

    fun addUseCase(): AddTodoItemUseCase

    fun deleteUseCase(): DeleteTodoItemUseCase

    fun itemsDoneCountUseCase(): GetDoneCountUseCase

    fun itemsWithVisibilityUseCase(): GetItemsWithVisibilityUseCase

    fun itemByIdUseCase(): GetTodoItemByIdOrNullUseCase
}