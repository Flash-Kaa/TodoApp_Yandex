package com.flasshka.todoapp.di.components

import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.modules.ItemsRepositoryModule
import dagger.Subcomponent

@ItemsRepositorySubcomponentScope
@Subcomponent(modules = [ItemsRepositoryModule::class])
internal interface ItemsRepositorySubcomponent {
    fun itemsUseCasesComponent(): ItemsUseCasesSubcomponent
}