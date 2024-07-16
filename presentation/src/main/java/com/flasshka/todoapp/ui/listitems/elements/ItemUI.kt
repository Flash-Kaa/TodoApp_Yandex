package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.graphics.Color
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
import com.flasshka.todoapp.ui.theme.GrayColor
import com.flasshka.todoapp.ui.theme.GreenColor
import com.flasshka.todoapp.ui.theme.RedColor
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = getAction(ListOfItemsActionType.OnChangeItem(item.id))
            )
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
    Checkbox(
        checked = item.completed,
        onCheckedChange = { getAction(ListOfItemsActionType.OnChangeDoneItem(item.id)).invoke() },
        colors = CheckboxDefaults.colors(
            checkedColor = GreenColor,
            uncheckedColor = if (item.importance == TodoItem.Importance.Important) RedColor
            else MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .testTag(TestTag.Checkbox.value)
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray)
            ),
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
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ImportanceIcon(
    item: TodoItem
) {
    if (item.importance == TodoItem.Importance.Basic) return

    val icon = if (item.importance == TodoItem.Importance.Important)
        ImageVector.vectorResource(id = R.drawable.baseline_priority_high_24)
    else
        ImageVector.vectorResource(id = R.drawable.baseline_arrow_downward_24)

    Icon(
        imageVector = icon,
        contentDescription = "importance icon",
        tint = if (item.importance == TodoItem.Importance.Important) RedColor else GrayColor
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
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyMedium
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