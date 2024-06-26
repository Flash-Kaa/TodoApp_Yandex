package com.flasshka.todoapp.ui.edititem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
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
fun EditItemUI(
    getName: () -> String,
    getImportance: () -> TodoItem.Importance,
    getDeadline: () -> Long?,
    deleteButtonIsEnabled: () -> Boolean,
    getAction: (EditItemActionType) -> (() -> Unit)
) {
    Column(horizontalAlignment = Alignment.Start) {
        TopButtons(
            getAction = getAction,
            modifier = Modifier
        )

        LazyColumn {
            item {
                NameField(
                    getName = getName,
                    getAction = getAction,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                ImportanceDropdownMenuItem(
                    getImportance = getImportance,
                    getAction = getAction
                )
            }

            item { Underline(modifier = Modifier.padding(horizontal = 16.dp)) }

            item { DeadlineItem(getDate = getDeadline, getAction = getAction) }

            item { Underline(modifier = Modifier.padding(top = 16.dp)) }

            item {
                DeleteButton(
                    isEnabled = deleteButtonIsEnabled,
                    getAction = getAction,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun ImportanceDropdownMenuItem(
    getImportance: () -> TodoItem.Importance,
    getAction: (EditItemActionType) -> (() -> Unit)
) {
    var needDropdown: Boolean by remember {
        mutableStateOf(false)
    }

    Row {
        ImportanceGetDropdown(
            getImportance = getImportance,
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
    getDate: () -> Long?,
    getAction: (EditItemActionType) -> (() -> Unit)
) {
    var checked by remember { mutableStateOf(getDate() != null) }
    var haveDate by remember { mutableStateOf(checked) }

    DeadlineSwitch(
        checked = checked,
        onCheckedChange = {
            checked = it

            if (!checked) {
                haveDate = false
                getAction(EditItemActionType.OnDeadlineChanged(null)).invoke()
            }
        },
        getDate = getDate,
        modifier = Modifier.padding(16.dp)
    )

    if (checked && !haveDate) {
        Calendar(
            onCancel = { checked = false },
            onDone = {
                haveDate = true
                getAction(EditItemActionType.OnDeadlineChanged(it)).invoke()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEditItemUI() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.back_primary)
        ) {
            EditItemUI(
                getName = { "" },
                getDeadline = { null },
                getImportance = { TodoItem.Importance.Common },
                deleteButtonIsEnabled = { true },
                getAction = { {} }
            )
        }
    }
}