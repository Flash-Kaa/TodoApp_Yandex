package com.flasshka.domain.usecases.token

import com.flasshka.domain.interfaces.TokenRepository

/**
 * Use case for fetch token
 */
class FetchTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke() {
        repository.fetchToken()
    }
}