package com.flasshka.data.di.modules

import com.flasshka.data.token.YandexOAuthDataSource
import com.flasshka.domain.interfaces.TokenDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TokenDataSourceModule {
    @Provides
    @Singleton
    fun tokenDataSource(): TokenDataSource = YandexOAuthDataSource()
}