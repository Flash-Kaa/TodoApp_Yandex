package com.flasshka.todoapp.ui.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.domain.entities.Token
import com.flasshka.domain.usecases.token.GetTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.actions.AuthorizationActionType
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.launch

/**
 * Manage authorization
 */
internal class AuthorizationVM(
    private val router: Router,
    private val fetchToken: GetTokenUseCase,
    private val updateToken: UpdateTokenUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            fetchToken()
        }
    }

    fun getAction(action: AuthorizationActionType): () -> Unit {
        return when (action) {
            is AuthorizationActionType.OnGetAnswer -> onTokenChanged(action.token)
            is AuthorizationActionType.OnExitAuth -> router::navigateToListOfItems
        }
    }

    private fun onTokenChanged(token: Token): () -> Unit {
        return {
            viewModelScope.launch { updateToken(token) }
            router.navigateToListOfItems()
        }
    }

    /**
     * Creates a AuthorizationVM without @AssistedInject
     */
    class FactoryWrapperWithUseCases(
        private val fetchToken: GetTokenUseCase,
        private val updateToken: UpdateTokenUseCase
    ) {
        inner class InnerFactory(
            private val router: Router
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthorizationVM(
                    fetchToken = fetchToken,
                    updateToken = updateToken,
                    router = router
                ) as T
            }
        }
    }
}