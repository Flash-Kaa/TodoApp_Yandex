package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DeleteButton(
    isEnabled: () -> Boolean,
    getAction: (EditItemActionType) -> (() -> Unit),
    modifier: Modifier = Modifier
) {
    val enabled = isEnabled()

    val color = if (enabled)
        colorResource(id = R.color.red)
    else
        colorResource(id = R.color.label_disable)

    val clickableModifier = if (enabled)
        modifier.clickable(onClick = getAction(EditItemActionType.OnDelete))
    else
        modifier

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = clickableModifier
            .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
            contentDescription = "delete",
            tint = color
        )

        Text(
            text = stringResource(R.string.delete),
            color = color,
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDeleteButton() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DeleteButton({ false }, { {} })
        }
    }
}