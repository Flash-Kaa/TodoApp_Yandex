package com.flasshka.todoapp.di.components

import com.flasshka.todoapp.di.ListViewModelScope
import com.flasshka.todoapp.di.modules.ListViewModelFactoryModule
import com.flasshka.todoapp.ui.listitems.ListVM
import dagger.Subcomponent

@ListViewModelScope
@Subcomponent(modules = [ListViewModelFactoryModule::class])
internal interface ListViewModelSubcomponent {
    fun provideFactoryWrapper(): ListVM.FactoryWrapperWithUseCases
}