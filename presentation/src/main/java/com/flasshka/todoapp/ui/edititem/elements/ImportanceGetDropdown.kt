package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.edititem.EditTodoItemState
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun ImportanceGetDropdown(
    state: EditTodoItemState,
    changeNeed: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { changeNeed(true) }
    ) {
        Text(
            text = stringResource(R.string.importance),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )

        Text(
            text = state.importance.toString(),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewImportanceDropdown() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val importance: TodoItem.Importance by remember {
                mutableStateOf(TodoItem.Importance.Basic)
            }

            ImportanceGetDropdown(
                state = EditTodoItemState.getNewState(),
                changeNeed = { },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}