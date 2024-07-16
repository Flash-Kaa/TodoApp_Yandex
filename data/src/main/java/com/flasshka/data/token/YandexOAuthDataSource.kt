package com.flasshka.data.token

import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.token.TokenDataSource

/**
 * Data Source impl for token
 */
class YandexOAuthDataSource : TokenDataSource {
    override var token: Token? = TokenSingleton.token
        set(value) {
            field = value
            TokenSingleton.token = value
        }

    override suspend fun haveLogin(): Boolean = token != null

    override suspend fun updateRevision(revision: Token.Revision) {
        token = token?.copy(revision = revision)
        TokenSingleton.token = token
    }

    private object TokenSingleton {
        var token: Token? = null
    }
}
