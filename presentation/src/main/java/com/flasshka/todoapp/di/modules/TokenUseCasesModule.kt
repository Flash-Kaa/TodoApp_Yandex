package com.flasshka.todoapp.di.modules

import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.domain.usecases.token.FetchTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TokenUseCasesModule {
    @Provides
    @Singleton
    fun provideFetchUseCase(
        repository: TokenRepository
    ): FetchTokenUseCase = FetchTokenUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateUseCase(
        repository: TokenRepository
    ): UpdateTokenUseCase = UpdateTokenUseCase(repository)
}