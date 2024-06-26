package com.flasshka.todoapp.ui.listitems

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerListUI(router: Router) {
    val context = LocalContext.current.applicationContext

    val listVM: ListVM = viewModel(
        factory = ListVM.Factory(
            router = router,
            showError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        )
    )

    val list: List<TodoItem> by listVM.getItems().collectAsState(initial = emptyList())
    val doneCounts: Int by listVM.getDoneCount().collectAsState(initial = 0)

    ListUI(
        doneCount = doneCounts,
        visibilityDoneON = listVM.visibility,
        items = list,
        getAction = listVM::getAction
    )
}