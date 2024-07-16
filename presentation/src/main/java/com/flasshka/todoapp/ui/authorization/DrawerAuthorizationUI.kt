package com.flasshka.todoapp.ui.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.data.token.PathToGetToken
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.isDarkThemeState
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DrawerAuthorizationUI(router: Router) {
    val context = LocalContext.current

    val viewModel: AuthorizationVM = viewModel(
        factory = context.appComponent.tokenRepositoryComponent()
            .tokenUseCasesComponent()
            .authorizationVMComponent()
            .provideFactoryWrapper()
            .InnerFactory(router)
    )

    DrawerAuthorizationUI(viewModel)
}

@Composable
private fun DrawerAuthorizationUI(
    viewModel: AuthorizationVM
) {
    TodoAppTheme(
        darkTheme = isDarkThemeState()
    ) {
        YandexAuthUI(
            url = PathToGetToken.URI,
            getAction = viewModel::getAction
        )
    }
}