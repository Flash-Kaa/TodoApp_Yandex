package com.flasshka.data.di.modules.binds

import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TokenRepositoryBindModule {
    @Binds
    @TokenRepositorySubcomponentScope
    abstract fun bindYandexOAuthRepositoryToInterface(
        yandexOAuthRepository: YandexOAuthRepository
    ): TokenRepository
}