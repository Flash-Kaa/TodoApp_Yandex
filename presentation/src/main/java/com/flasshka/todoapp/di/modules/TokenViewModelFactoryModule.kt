package com.flasshka.todoapp.di.modules

import com.flasshka.domain.usecases.token.GetTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.di.AuthorizationViewModelScope
import com.flasshka.todoapp.ui.authorization.AuthorizationVM
import dagger.Module
import dagger.Provides

@Module
internal class TokenViewModelFactoryModule {
    @Provides
    @AuthorizationViewModelScope
    fun provideAuthorizationVMFactory(
        fetchToken: GetTokenUseCase,
        updateToken: UpdateTokenUseCase
    ): AuthorizationVM.FactoryWrapperWithUseCases = AuthorizationVM.FactoryWrapperWithUseCases(
        fetchToken = fetchToken,
        updateToken = updateToken
    )
}