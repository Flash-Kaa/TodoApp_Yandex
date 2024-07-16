package com.flasshka.todoapp.di.modules

import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.domain.usecases.token.GetTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.di.TokenUseCaseScope
import dagger.Module
import dagger.Provides

@Module
class TokenUseCasesModule {
    @Provides
    @TokenUseCaseScope
    fun provideFetchUseCase(
        repository: TokenRepository
    ): GetTokenUseCase = GetTokenUseCase(repository)

    @Provides
    @TokenUseCaseScope
    fun provideUpdateUseCase(
        repository: TokenRepository
    ): UpdateTokenUseCase = UpdateTokenUseCase(repository)
}