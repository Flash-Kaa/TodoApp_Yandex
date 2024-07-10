package com.flasshka.data.di.modules.binds

import com.flasshka.data.token.YandexOAuthDataSource
import com.flasshka.domain.interfaces.TokenDataSource
import dagger.Binds
import dagger.Module

@Module
internal abstract class TokenDataSourceBindModule {
    @Binds
    abstract fun bindYandexOAuthTokenDataSourceToInterface(
        yandexOAuthDataSource: YandexOAuthDataSource
    ): TokenDataSource
}