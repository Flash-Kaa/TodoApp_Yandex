package com.flasshka.data.di.modules

import com.flasshka.data.di.modules.binds.TokenDataSourceBindModule
import com.flasshka.data.token.YandexOAuthDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TokenDataSourceBindModule::class])
internal class TokenDataSourceModule {
    @Provides
    @Singleton
    fun provideTokenDataSource(): YandexOAuthDataSource = YandexOAuthDataSource()
}