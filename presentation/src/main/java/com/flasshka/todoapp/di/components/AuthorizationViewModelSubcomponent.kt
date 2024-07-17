package com.flasshka.todoapp.di.components

import com.flasshka.todoapp.di.AuthorizationViewModelScope
import com.flasshka.todoapp.di.modules.TokenViewModelFactoryModule
import com.flasshka.todoapp.ui.authorization.AuthorizationVM
import dagger.Subcomponent

@AuthorizationViewModelScope
@Subcomponent(modules = [TokenViewModelFactoryModule::class])
internal interface AuthorizationViewModelSubcomponent {
    fun provideFactoryWrapper(): AuthorizationVM.FactoryWrapperWithUseCases
}