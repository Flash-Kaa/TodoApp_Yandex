package com.flasshka.data.di.modules.binds

import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class TokenRepositoryBindModule {
    @Binds
    @Singleton
    abstract fun bindYandexOAuthRepositoryToInterface(
        yandexOAuthRepository: YandexOAuthRepository
    ): TokenRepository
}