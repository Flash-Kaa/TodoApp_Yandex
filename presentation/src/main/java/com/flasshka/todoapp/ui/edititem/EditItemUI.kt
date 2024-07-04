package com.flasshka.todoapp.ui.edititem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.edititem.elements.Calendar
import com.flasshka.todoapp.ui.edititem.elements.DeadlineSwitch
import com.flasshka.todoapp.ui.edititem.elements.DeleteButton
import com.flasshka.todoapp.ui.edititem.elements.ImportanceDropdownMenu
import com.flasshka.todoapp.ui.edititem.elements.ImportanceGetDropdown
import com.flasshka.todoapp.ui.edititem.elements.NameField
import com.flasshka.todoapp.ui.edititem.elements.TopButtons
import com.flasshka.todoapp.ui.edititem.elements.Underline
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
internal fun EditItemUI(
    snackbarHostState: SnackbarHostState,
    state: EditTodoItemState,
    deleteButtonIsEnabled: () -> Boolean,
    getAction: (EditItemActionType) -> (() -> Unit),
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        content = { ContentColumn(it, getAction, state, deleteButtonIsEnabled) }
    )
}

@Composable
private fun ContentColumn(
    padding: PaddingValues,
    getAction: (EditItemActionType) -> () -> Unit,
    state: EditTodoItemState,
    deleteButtonIsEnabled: () -> Boolean
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(padding)
    ) {
        TopButtons(getAction = getAction)

        LazyColumnOfEditField(
            state = state,
            getAction = getAction,
            deleteButtonIsEnabled = deleteButtonIsEnabled
        )
    }
}

@Composable
private fun LazyColumnOfEditField(
    state: EditTodoItemState,
    getAction: (EditItemActionType) -> () -> Unit,
    deleteButtonIsEnabled: () -> Boolean
) {
    LazyColumn {
        item { NameField(state, getAction, Modifier.padding(16.dp)) }

        item { ImportanceDropdownMenuItem(state, getAction) }

        item { Underline(modifier = Modifier.padding(horizontal = 16.dp)) }

        item { DeadlineItem(state = state, getAction = getAction) }

        item { Underline(modifier = Modifier.padding(top = 16.dp)) }

        item { DeleteButton(deleteButtonIsEnabled, getAction, Modifier.padding(16.dp)) }
    }
}

@Composable
private fun ImportanceDropdownMenuItem(
    state: EditTodoItemState,
    getAction: (EditItemActionType) -> (() -> Unit)
) {
    var needDropdown: Boolean by remember { mutableStateOf(false) }

    Row {
        ImportanceGetDropdown(
            state = state,
            changeNeed = { needDropdown = it },
            modifier = Modifier.padding(16.dp)
        )

        ImportanceDropdownMenu(
            expanded = needDropdown,
            changeNeed = { needDropdown = it },
            getAction = getAction,
            offset = DpOffset(x = 16.dp, y = (-60).dp)
        )
    }
}

@Composable
private fun DeadlineItem(
    state: EditTodoItemState,
    getAction: (EditItemActionType) -> (() -> Unit)
) {
    var needCalendar by remember { mutableStateOf(false) }
    val onCheckedChange: (Boolean) -> Unit = {
        if (!it) getAction(EditItemActionType.OnDeadlineChanged(null)).invoke()
        else needCalendar = true
    }

    DeadlineSwitch(
        checked = state.deadLine != null,
        onCheckedChange = onCheckedChange,
        state = state,
        modifier = Modifier.padding(16.dp)
    )

    if (!needCalendar) return
    Calendar(
        onCancel = { needCalendar = false },
        onDone = {
            needCalendar = false
            getAction(EditItemActionType.OnDeadlineChanged(it)).invoke()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEditItemUI() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EditItemUI(
                snackbarHostState = remember { SnackbarHostState() },
                state = EditTodoItemState.getNewState(),
                deleteButtonIsEnabled = { true },
                getAction = { {} }
            )
        }
    }
}