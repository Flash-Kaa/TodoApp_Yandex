package com.flasshka.todoapp.di.components

import com.flasshka.domain.usecases.token.FetchTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.di.TokenUseCaseScope
import com.flasshka.todoapp.di.modules.TokenUseCasesModule
import dagger.Subcomponent

@TokenUseCaseScope
@Subcomponent(modules = [TokenUseCasesModule::class])
internal interface TokenUseCaseSubcomponents {
    fun authorizationVMComponent(): AuthorizationViewModelSubcomponent

    fun fetchUseCase(): FetchTokenUseCase

    fun updateUseCase(): UpdateTokenUseCase
}