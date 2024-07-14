package com.flasshka.data.token

import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.TokenDataSource
import com.flasshka.domain.interfaces.TokenRepository

/**
 * repository for using token
 */
class YandexOAuthRepository(
    private val dataSource: TokenDataSource
) : TokenRepository {

    override suspend fun getToken(): Token? {
        return dataSource.token
    }

    override suspend fun updateToken(token: Token) {
        dataSource.token = token
    }

    override suspend fun haveLogin(): Boolean {
        return dataSource.haveLogin()
    }

    override suspend fun updateRevision(revision: Token.Revision) {
        dataSource.updateRevision(revision)
    }
}