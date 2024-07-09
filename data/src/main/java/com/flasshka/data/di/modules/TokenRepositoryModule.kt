package com.flasshka.data.di.modules

import com.flasshka.data.token.YandexOAuthRepository
import com.flasshka.domain.interfaces.TokenDataSource
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TokenDataSourceModule::class])
class TokenRepositoryModule {
    @Provides
    @Singleton
    fun tokenRepository(dataSource: TokenDataSource): TokenRepository {
        return YandexOAuthRepository(dataSource)
    }
}