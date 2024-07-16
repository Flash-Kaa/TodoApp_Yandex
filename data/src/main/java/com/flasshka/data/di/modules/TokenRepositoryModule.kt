package com.flasshka.data.di.modules

import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.di.modules.binds.TokenRepositoryBindModule
import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.token.TokenDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [TokenRepositoryBindModule::class, TokenDataSourceModule::class])
class TokenRepositoryModule {
    @Provides
    @TokenRepositorySubcomponentScope
    fun provideTokenRepository(
        dataSource: TokenDataSource
    ): YandexOAuthRepository = YandexOAuthRepository(dataSource)
}