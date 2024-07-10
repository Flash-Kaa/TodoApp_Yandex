package com.flasshka.data.di.modules

import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.di.modules.binds.TokenDataSourceBindModule
import com.flasshka.data.token.YandexOAuthDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [TokenDataSourceBindModule::class])
internal class TokenDataSourceModule {
    @Provides
    @TokenRepositorySubcomponentScope
    fun provideTokenDataSource(): YandexOAuthDataSource = YandexOAuthDataSource()
}