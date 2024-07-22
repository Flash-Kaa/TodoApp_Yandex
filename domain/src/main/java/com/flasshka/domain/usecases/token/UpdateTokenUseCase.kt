package com.flasshka.domain.usecases.token

import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

/**
 * Use case for update token
 */
class UpdateTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(token: Token) {
        runWithSupervisorInBackground {
            repository.updateToken(token)
        }
    }
}