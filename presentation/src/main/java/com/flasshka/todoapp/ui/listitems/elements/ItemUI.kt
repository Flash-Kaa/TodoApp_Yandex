package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.TestTag
import com.flasshka.todoapp.actions.ListOfItemsActionType

@Composable
fun ItemUI(
    item: TodoItem,
    getAction: (ListOfItemsActionType) -> (() -> Unit),

    modifier: Modifier = Modifier
) {
    ListItem(
        leadingContent = {
            val uncheckedColor = if (item.importance == TodoItem.Importance.Urgently)
                colorResource(id = R.color.red)
            else colorResource(id = R.color.label_tertiary)

            Checkbox(
                checked = item.completed,
                onCheckedChange = {
                    getAction(ListOfItemsActionType.OnChangeDoneItem(item.id)).invoke()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.green),
                    uncheckedColor = uncheckedColor
                ),
                modifier = Modifier.testTag(TestTag.Checkbox.value)
            )
        },
        headlineContent = { TextName(item = item) },
        supportingContent = { DateDrawer(item = item) },
        trailingContent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_info_outline_24),
                contentDescription = "info_btn",
                tint = colorResource(id = R.color.label_tertiary)
            )
        },
        modifier = modifier
            .clickable(onClick = getAction(ListOfItemsActionType.OnChangeItem(item.id)))
            .testTag(TestTag.ListItem.value),
        colors = ListItemDefaults.colors(
            containerColor = colorResource(id = R.color.back_secondary)
        )
    )
}

@Composable
private fun TextName(
    item: TodoItem
) {
    Row {
        ImportanceIcon(item)

        val color = if (item.completed)
            colorResource(id = R.color.label_tertiary)
        else
            colorResource(id = R.color.label_primary)

        Text(
            text = item.text,
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            textDecoration = if (!item.completed) null else TextDecoration.LineThrough,
            color = color,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ImportanceIcon(
    item: TodoItem
) {
    if (item.importance != TodoItem.Importance.Common) {
        val color = if (item.importance == TodoItem.Importance.Urgently)
            colorResource(id = R.color.red)
        else
            colorResource(id = R.color.gray)

        val icon = if (item.importance == TodoItem.Importance.Urgently)
            ImageVector.vectorResource(id = R.drawable.baseline_priority_high_24)
        else
            ImageVector.vectorResource(id = R.drawable.baseline_arrow_downward_24)

        Icon(
            imageVector = icon,
            contentDescription = "importance icon",
            tint = color
        )
    }

}

@Composable
private fun DateDrawer(
    item: TodoItem
) {
    item.deadLine?.let {
        val context = LocalContext.current
        val formatter = android.text.format.DateFormat.getDateFormat(context)

        Text(
            text = formatter.format(it),
            fontWeight = FontWeight(400),
            fontSize = 14.sp,
            color = colorResource(id = R.color.label_tertiary)
        )
    }
}