package com.flasshka.domain.usecases.token

import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.TokenRepository

/**
 * Use case for update token
 */
class UpdateTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(token: Token) {
        repository.updateToken(token)
    }
}