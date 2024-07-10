package com.flasshka.data.di.modules

import com.flasshka.data.di.modules.binds.TokenRepositoryBindModule
import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.TokenDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TokenRepositoryBindModule::class, TokenDataSourceModule::class])
class TokenRepositoryModule {
    @Provides
    @Singleton
    fun provideTokenRepository(
        dataSource: TokenDataSource
    ): YandexOAuthRepository = YandexOAuthRepository(dataSource)
}