package com.flasshka.todoapp.ui.info

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerInfoUI(
    router: Router,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: InfoVM = viewModel(
        factory = InfoVM.InnerFactory(router)
    )

    InfoUI(
        snackbarHostState = snackbarHostState,
        getAction = viewModel::getAction
    )
}