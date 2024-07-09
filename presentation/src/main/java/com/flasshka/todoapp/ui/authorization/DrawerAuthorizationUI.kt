package com.flasshka.todoapp.ui.authorization

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.data.di.components.DaggerTokenRepositoryComponent
import com.flasshka.data.token.PathToGetToken
import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.domain.usecases.token.FetchTokenUseCase
import com.flasshka.domain.usecases.token.UpdateTokenUseCase
import com.flasshka.todoapp.navigation.Router

private val repository = DaggerTokenRepositoryComponent.create().getTokenRepository()

@Composable
fun DrawerAuthorizationUI(router: Router) {
    val authVM: AuthorizationVM = viewModel(
        factory = createAuthFactory(repository, router)
    )

    YandexAuthUI(
        url = PathToGetToken.URI,
        getAction = authVM::getAction
    )
}

fun createAuthFactory(repository: TokenRepository, router: Router) =
    AuthorizationVM.Factory(
        router = router,
        fetchToken = FetchTokenUseCase(repository),
        updateToken = UpdateTokenUseCase(repository)
    )