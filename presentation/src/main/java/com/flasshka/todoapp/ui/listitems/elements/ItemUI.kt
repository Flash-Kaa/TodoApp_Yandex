package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.TestTag
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.ui.theme.DarkThemeGray
import com.flasshka.todoapp.ui.theme.DarkThemeGreen
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeGray
import com.flasshka.todoapp.ui.theme.LightThemeGreen
import com.flasshka.todoapp.ui.theme.LightThemeRed
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import java.util.Calendar

@Composable
fun ItemUI(
    item: TodoItem,
    getAction: (ListOfItemsActionType) -> (() -> Unit),

    modifier: Modifier = Modifier
) {
    ListItem(
        leadingContent = { CheckboxContent(item, getAction) },
        headlineContent = { TextName(item = item) },
        supportingContent = { DateDrawer(item = item) },
        trailingContent = { TrailingIcon() },
        modifier = modifier
            .clickable(onClick = getAction(ListOfItemsActionType.OnChangeItem(item.id)))
            .testTag(TestTag.ListItem.value),
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun TrailingIcon() {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_info_outline_24),
        contentDescription = "info_btn",
        tint = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun CheckboxContent(
    item: TodoItem,
    getAction: (ListOfItemsActionType) -> () -> Unit
) {
    val uncheckedColor =
        if (item.importance == TodoItem.Importance.Important && isSystemInDarkTheme()) {
            DarkThemeRed
        } else if (item.importance == TodoItem.Importance.Important) {
            LightThemeRed
        } else {
            MaterialTheme.colorScheme.tertiary
        }

    Checkbox(
        checked = item.completed,
        onCheckedChange = { getAction(ListOfItemsActionType.OnChangeDoneItem(item.id)).invoke() },
        colors = CheckboxDefaults.colors(
            checkedColor = if (isSystemInDarkTheme()) DarkThemeGreen else LightThemeGreen,
            uncheckedColor = uncheckedColor
        ),
        modifier = Modifier.testTag(TestTag.Checkbox.value)
    )
}

@Composable
private fun TextName(
    item: TodoItem
) {
    Row {
        val textDecoration = if (!item.completed) null else TextDecoration.LineThrough
        val textColor = if (item.completed) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.primary
        }

        ImportanceIcon(item)
        Text(
            text = item.text,
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            textDecoration = textDecoration,
            color = textColor,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ImportanceIcon(
    item: TodoItem
) {
    if (item.importance == TodoItem.Importance.Basic) return

    val color = if (item.importance == TodoItem.Importance.Important)
        if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed
    else
        if (isSystemInDarkTheme()) DarkThemeGray else LightThemeGray

    val icon = if (item.importance == TodoItem.Importance.Important)
        ImageVector.vectorResource(id = R.drawable.baseline_priority_high_24)
    else
        ImageVector.vectorResource(id = R.drawable.baseline_arrow_downward_24)

    Icon(
        imageVector = icon,
        contentDescription = "importance icon",
        tint = color
    )
}

@Composable
private fun DateDrawer(
    item: TodoItem
) {
    if (item.deadLine == null) return

    val context = LocalContext.current
    val formatter = android.text.format.DateFormat.getDateFormat(context)

    Text(
        text = formatter.format(item.deadLine!!),
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemUI() {
    TodoAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val item = remember {
                TodoItem(
                    id = "123",
                    text = "text",
                    TodoItem.Importance.Important,
                    created = Calendar.getInstance().time,
                    deadLine = Calendar.getInstance().time
                )
            }

            ItemUI(item = item, getAction = { {} })
        }
    }
}