package com.flasshka.todoapp.ui.listitems

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.ui.listitems.elements.CreateFAB
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun ListUI(
    getAction: (ListOfItemsActionType) -> (() -> Unit)
) {
    CreateFAB(
        onClick = getAction(ListOfItemsActionType.OnCreate)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewListUI() {
    TodoAppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ListUI({ {} })
        }
    }
}