package com.flasshka.todoapp.ui.listitems

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.appComponent
import com.flasshka.todoapp.isDarkThemeState
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DrawerListUI(
    router: Router,
    snackbarHostState: SnackbarHostState
) {
    val listVM: ListVM = viewModel(
        factory = LocalContext.current.appComponent
            .tokenRepositoryComponent()
            .itemsRepositoryComponent()
            .itemsUseCasesComponent()
            .listVMComponent()
            .provideFactoryWrapper()
            .InnerFactory(router)
    )

    DrawerListUI(listVM, snackbarHostState)
}

@Composable
private fun DrawerListUI(
    listVM: ListVM,
    snackbarHostState: SnackbarHostState
) {
    val list: List<TodoItem> by listVM.getItems().collectAsState(initial = emptyList())
    val doneCounts: Int by listVM.getDoneCount().collectAsState(initial = 0)

    TodoAppTheme(
        darkTheme = isDarkThemeState()
    ) {
        ListUI(
            snackbarHostState = snackbarHostState,
            doneCount = doneCounts,
            visibilityDoneON = listVM.visibility,
            items = list,
            getAction = listVM::getAction
        )
    }
}