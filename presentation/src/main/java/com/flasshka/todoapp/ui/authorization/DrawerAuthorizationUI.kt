package com.flasshka.todoapp.ui.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.data.token.PathToGetToken
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerAuthorizationUI(router: Router) {
    val context = LocalContext.current

    val authVM: AuthorizationVM = viewModel(
        factory = context.appComponent.provideAuthVmFactory().InnerFactory(router)
    )

    YandexAuthUI(
        url = PathToGetToken.URI,
        getAction = authVM::getAction
    )
}