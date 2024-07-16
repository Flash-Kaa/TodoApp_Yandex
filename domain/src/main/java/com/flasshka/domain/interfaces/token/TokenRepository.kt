package com.flasshka.domain.interfaces.token

import com.flasshka.domain.entities.Token

interface TokenRepository {
    suspend fun getToken(): Token?

    suspend fun updateToken(token: Token)

    suspend fun haveLogin(): Boolean

    suspend fun updateRevision(revision: Token.Revision)
}