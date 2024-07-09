package com.flasshka.domain.interfaces

import com.flasshka.domain.entities.Token
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {
    val hasLogin: StateFlow<Boolean>

    suspend fun fetchToken()

    suspend fun updateToken(token: Token)
}