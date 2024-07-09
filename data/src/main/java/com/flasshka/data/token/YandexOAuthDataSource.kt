package com.flasshka.data.token

import com.flasshka.data.network.ServiceConstants
import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.TokenDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Data Source impl for token
 */
class YandexOAuthDataSource @Inject constructor() : TokenDataSource {
    override suspend fun updateToken(token: Token) {
        ServiceConstants.OAthWithToken = token
    }

    override suspend fun getToken(): Flow<Token> {
        return flow {
            emit(ServiceConstants.OAthWithToken)
        }
    }
}