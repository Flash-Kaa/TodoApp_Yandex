package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.theme.DarkThemeLabelDisable
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeLabelDisable
import com.flasshka.todoapp.ui.theme.LightThemeRed
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DeleteButton(
    isEnabled: () -> Boolean,
    getAction: (EditItemActionType) -> (() -> Unit),
    modifier: Modifier = Modifier
) {
    val enabled = isEnabled()
    val color = if (enabled)
        if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed
    else
        if (isSystemInDarkTheme()) DarkThemeLabelDisable else LightThemeLabelDisable

    val clickableModifier = if (enabled)
        modifier.clickable(onClick = getAction(EditItemActionType.OnDelete))
    else
        modifier

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = clickableModifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        DeleteIcon(color)
        DeleteText(color)
    }
}

@Composable
private fun DeleteText(color: Color) {
    Text(
        text = stringResource(R.string.delete),
        color = color,
        modifier = Modifier.padding(start = 16.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight(400)
    )
}

@Composable
private fun DeleteIcon(color: Color) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
        contentDescription = "delete",
        tint = color
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDeleteButton() {
    TodoAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DeleteButton({ true }, { {} })
            DeleteButton({ false }, { {} })
        }
    }
}