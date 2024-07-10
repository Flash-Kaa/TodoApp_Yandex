package com.flasshka.todoapp.di.components

import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.modules.ItemsRepositoryModule
import com.flasshka.data.di.modules.binds.RepositoryBindModule
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Subcomponent

@ItemsRepositorySubcomponentScope
@Subcomponent(modules = [ItemsRepositoryModule::class, RepositoryBindModule::class])
internal interface ItemsRepositorySubcomponent {
    fun itemsUseCasesComponent(): ItemsUseCasesSubcomponent

    fun provideItemsRepository(): TodoItemRepository
}