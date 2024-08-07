package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.edititem.EditTodoItemState
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun ImportanceGetChooser(
    state: EditTodoItemState,
    openChooser: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = openChooser
            )
            .padding(16.dp)
    ) {
        Title()
        CurrentImportance(state)
    }
}

@Composable
private fun CurrentImportance(state: EditTodoItemState) {
    Text(
        text = state.importance.toString(),
        color = MaterialTheme.colorScheme.tertiary,
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun Title() {
    Text(
        text = stringResource(R.string.importance),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewImportanceDropdown() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ImportanceGetChooser(
                state = EditTodoItemState.getNewState(),
                openChooser = { },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}