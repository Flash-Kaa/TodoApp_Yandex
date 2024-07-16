package com.flasshka.data.di.modules.binds

import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.token.TokenRepository
import dagger.Binds
import dagger.Module

@Module
abstract class TokenRepositoryBindModule {
    @Binds
    @TokenRepositorySubcomponentScope
    abstract fun bindYandexOAuthRepositoryToInterface(
        yandexOAuthRepository: YandexOAuthRepository
    ): TokenRepository
}