package com.flasshka.todoapp.di.components

import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.di.modules.TokenRepositoryModule
import com.flasshka.data.di.modules.binds.TokenRepositoryBindModule
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Subcomponent

@TokenRepositorySubcomponentScope
@Subcomponent(modules = [TokenRepositoryModule::class, TokenRepositoryBindModule::class])
internal interface TokenRepositorySubcomponent {
    fun tokenUseCasesComponent(): TokenUseCaseSubcomponents

    fun provideTokenRepository(): TokenRepository
}