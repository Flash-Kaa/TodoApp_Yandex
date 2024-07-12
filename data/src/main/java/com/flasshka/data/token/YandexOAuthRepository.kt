package com.flasshka.data.token

import com.flasshka.domain.entities.Token
import com.flasshka.domain.interfaces.TokenDataSource
import com.flasshka.domain.interfaces.TokenRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * repository for using token
 */
class YandexOAuthRepository(
    private val dataSource: TokenDataSource
) : TokenRepository {
    private val _hasLogin: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val hasLogin: StateFlow<Boolean> = _hasLogin.asStateFlow()

    override suspend fun fetchToken() {
        runWithSupervisor {
            dataSource.getToken().collect { collect ->
                _hasLogin.update { collect != Token() }
            }
        }
    }

    override suspend fun updateToken(token: Token) {
        runWithSupervisor {
            _hasLogin.update { token != Token() }

            dataSource.updateToken(token)
        }
    }

    private suspend fun runWithSupervisor(
        content: suspend CoroutineScope.() -> Unit
    ) {
        supervisorScope {
            launch(
                context = Dispatchers.IO + CoroutineExceptionHandler { _, _ -> },
                block = content
            )
        }
    }
}