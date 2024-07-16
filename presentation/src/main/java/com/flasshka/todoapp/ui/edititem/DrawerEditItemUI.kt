package com.flasshka.todoapp.ui.edititem

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.isDarkThemeState
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: EditItemVM = viewModel(
        factory = LocalContext.current.appComponent
            .tokenRepositoryComponent()
            .itemsRepositoryComponent()
            .itemsUseCasesComponent()
            .editItemVMComponent()
            .provideFactoryWrapper()
            .InnerFactory(router, itemId),
    )


    DrawerEditItemUI(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun DrawerEditItemUI(
    viewModel: EditItemVM,
    snackbarHostState: SnackbarHostState
) {
    val state: EditTodoItemState by viewModel.state.collectAsState(EditTodoItemState.getNewState())

    TodoAppTheme(
        darkTheme = isDarkThemeState()
    ) {
        EditItemUI(
            snackbarHostState = snackbarHostState,
            state = state,
            deleteButtonIsEnabled = viewModel::getDeleteButtonIsEnabled,
            getAction = viewModel::getAction,
        )
    }
}