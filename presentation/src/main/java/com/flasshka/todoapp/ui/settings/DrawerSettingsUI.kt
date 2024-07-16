package com.flasshka.todoapp.ui.settings

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.getDarkThemeState
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DrawerSettingsUI(
    router: Router,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: SettingsVM = viewModel(
        factory = LocalContext.current.appComponent
            .settingsRepositoryComponent()
            .settingsUseCasesComponent()
            .settingsViewModelComponent()
            .provideFactoryWrapper()
            .InnerFactory(router)
    )

    DrawerSettingsUI(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun DrawerSettingsUI(
    viewModel: SettingsVM,
    snackbarHostState: SnackbarHostState
) {
    val state: SettingsStateUI by viewModel.state.collectAsState()

    TodoAppTheme(darkTheme = getDarkThemeState()) {
        SettingsUI(
            state = state,
            snackbarHostState = snackbarHostState,
            saveIsEnabled = viewModel::saveIsEnabled,
            getAction = viewModel::getAction
        )
    }
}