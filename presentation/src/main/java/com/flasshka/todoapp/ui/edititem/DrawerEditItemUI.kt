package com.flasshka.todoapp.ui.edititem

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: EditItemVM = viewModel(
        factory = LocalContext.current.appComponent.itemsRepositoryComponent()
            .itemsUseCasesComponent()
            .editItemVMComponent()
            .provideFactoryWrapper()
            .InnerFactory(router, itemId),
    )

    val state: EditTodoItemState by viewModel.state.collectAsState(EditTodoItemState.getNewState())

    EditItemUI(
        snackbarHostState = snackbarHostState,
        state = state,
        deleteButtonIsEnabled = viewModel::getDeleteButtonIsEnabled,
        getAction = viewModel::getAction,
    )
}