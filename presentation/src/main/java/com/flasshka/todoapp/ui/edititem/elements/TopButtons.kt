package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun TopButtons(
    getAction: (EditItemActionType) -> (() -> Unit),
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .shadow(0.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ) {
            BackIcon(getAction)
            SaveText(getAction)
        }
    }
}

@Composable
private fun SaveText(getAction: (EditItemActionType) -> () -> Unit) {
    Text(
        text = stringResource(R.string.save),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = getAction(EditItemActionType.OnSave)
            )
            .padding(16.dp),
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        color = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun BackIcon(getAction: (EditItemActionType) -> () -> Unit) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_close_24),
        contentDescription = "back to list",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = getAction(EditItemActionType.OnExit)
            )
            .padding(16.dp)
            .size(30.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTopButtons() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopButtons({ {} })
        }
    }
}