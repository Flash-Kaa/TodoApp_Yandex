package com.flasshka.todoapp.ui.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.domain.entities.Token
import com.flasshka.domain.usecases.token.FetchTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.actions.AuthorizationActions
import com.flasshka.todoapp.navigation.Router
import kotlinx.coroutines.launch

/**
 * Manage authorization
 */
class AuthorizationVM(
    private val router: Router,
    private val fetchToken: FetchTokenUseCase,
    private val updateToken: UpdateTokenUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            fetchToken()
        }
    }

    fun getAction(action: AuthorizationActions): () -> Unit {
        return when (action) {
            is AuthorizationActions.OnGetAnswer -> onTokenChanged(action.token)
            is AuthorizationActions.OnExitAuth -> router::navigateToListOfItems
        }
    }

    private fun onTokenChanged(token: Token): () -> Unit {
        return {
            viewModelScope.launch { updateToken(token) }
            router.navigateToListOfItems()
        }
    }

    class Factory(
        private val router: Router,
        private val fetchToken: FetchTokenUseCase,
        private val updateToken: UpdateTokenUseCase
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