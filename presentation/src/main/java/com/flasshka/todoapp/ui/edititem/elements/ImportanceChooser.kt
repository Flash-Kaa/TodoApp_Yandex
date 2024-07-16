package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.edititem.EditTodoItemState
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeRed

@Composable
fun ImportanceChooser(
    state: EditTodoItemState,
    getAction: (EditItemActionType) -> () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        TodoItem.Importance.entries.forEach { importance ->
            ImportanceDrawer(
                state = state,
                importance = importance,
                getAction = getAction
            )
        }
    }
}

@Composable
private fun ImportanceDrawer(
    state: EditTodoItemState,
    importance: TodoItem.Importance,
    getAction: (EditItemActionType) -> (() -> Unit),
) {
    val color = if (importance == TodoItem.Importance.Important)
        if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed
    else
        MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .isChosen(state, importance)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = getAction(EditItemActionType.OnImportanceChanged(importance))
            )
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = importance.toString(),
            color = color,
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Modifier.isChosen(
    state: EditTodoItemState,
    curImportance: TodoItem.Importance
): Modifier {
    if (state.importance != curImportance) {
        return this
    }

    val withBackground: Modifier
    if (curImportance == TodoItem.Importance.Important) {
        val isDark = isSystemInDarkTheme()
        val animateColor = remember {
            Animatable(
                if (isDark) DarkThemeRed else LightThemeRed
            )
        }

        LaunchedEffect(key1 = Unit) {
            animateColor.animateTo(
                Color.Transparent,
                tween(1000)
            )
        }

        withBackground = this.background(animateColor.value)
    } else {
        withBackground = this
    }

    return withBackground.border(
        0.3.dp,
        MaterialTheme.colorScheme.primary,
        RoundedCornerShape(16.dp)
    )
}