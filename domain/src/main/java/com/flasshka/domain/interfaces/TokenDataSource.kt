package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.Token

interface TokenDataSource {
    var token: Token?

    suspend fun haveLogin(): Boolean

    suspend fun updateRevision(revision: Token.Revision)
}