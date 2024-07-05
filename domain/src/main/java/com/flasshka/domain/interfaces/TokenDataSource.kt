package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.Token
import kotlinx.coroutines.flow.Flow

interface TokenDataSource {
    suspend fun updateToken(token: Token)

    suspend fun getToken(): Flow<Token>
}