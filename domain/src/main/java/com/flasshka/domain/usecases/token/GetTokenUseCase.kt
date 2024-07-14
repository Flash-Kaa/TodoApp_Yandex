package com.flasshka.domain.usecases.token

import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

/**
 * Use case for fetch token
 */
class GetTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke() {
        runWithSupervisorInBackground {
            repository.getToken()
        }
    }
}