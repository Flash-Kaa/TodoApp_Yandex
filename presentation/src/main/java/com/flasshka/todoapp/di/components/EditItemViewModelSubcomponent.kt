package com.flasshka.todoapp.di.components

import com.flasshka.todoapp.di.EditItemViewModelScope
import com.flasshka.todoapp.di.modules.EditItemViewModelFactoryModule
import com.flasshka.todoapp.ui.edititem.EditItemVM
import dagger.Subcomponent

@EditItemViewModelScope
@Subcomponent(modules = [EditItemViewModelFactoryModule::class])
internal interface EditItemViewModelSubcomponent {
    fun provideFactoryWrapper(): EditItemVM.FactoryWrapperWithUseCases
}