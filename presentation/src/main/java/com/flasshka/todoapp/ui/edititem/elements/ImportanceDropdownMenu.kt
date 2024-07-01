package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeRed
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun ImportanceDropdownMenu(
    expanded: Boolean,
    changeNeed: (Boolean) -> Unit,
    getAction: (EditItemActionType) -> (() -> Unit),
    offset: DpOffset = DpOffset.Zero
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { changeNeed(false) },
        offset = offset,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .widthIn(min = 190.dp)
            .shadow(0.1.dp, RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
    ) {
        TodoItem.Importance.entries.forEach { importance ->
            ImportanceDrawer(
                importance = importance,
                changeNeed = changeNeed,
                getAction = getAction
            )
        }
    }
}

@Composable
private fun ImportanceDrawer(
    importance: TodoItem.Importance,
    changeNeed: (Boolean) -> Unit,
    getAction: (EditItemActionType) -> (() -> Unit),
) {
    val color = if (importance == TodoItem.Importance.Important)
        if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed
    else
        MaterialTheme.colorScheme.primary

    DropdownMenuItem(
        text = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.heightIn(min = 60.dp)
            ) {
                Text(
                    text = importance.toString(),
                    color = color,
                    fontWeight = FontWeight(400),
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                )
            }
        },
        onClick = {
            getAction(EditItemActionType.OnImportanceChanged(importance)).invoke()
            changeNeed(false)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewImportanceDropdownMenu() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var expended: Boolean by remember {
                mutableStateOf(true)
            }

            ImportanceDropdownMenu(
                expanded = expended,
                changeNeed = { expended = it },
                getAction = { {} }
            )
        }
    }

}