package com.flasshka.todoapp.ui.edititem

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasshka.todoapp.navigation.Router

@Composable
fun DrawerEditItemUI(
    router: Router,
    itemId: String? = null
) {
    val context = LocalContext.current.applicationContext

    val editItemVM: EditItemVM = viewModel(
        factory = EditItemVM.Factory(
            router = router,
            itemId = itemId,
            showError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        ),
    )

    EditItemUI(
        state1 = editItemVM.state,
        deleteButtonIsEnabled = editItemVM::getDeleteButtonIsEnabled,
        getAction = editItemVM::getAction
    )
}