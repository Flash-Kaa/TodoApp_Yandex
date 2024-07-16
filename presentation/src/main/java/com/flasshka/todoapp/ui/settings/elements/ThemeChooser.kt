package com.flasshka.todoapp.ui.settings.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.flasshka.domain.entities.Settings
import com.flasshka.todoapp.actions.SettingsActionType
import com.flasshka.todoapp.ui.settings.SettingsStateUI

@Composable
fun ThemeChooser(
    state: SettingsStateUI,
    getAction: (SettingsActionType) -> (() -> Unit)
) {
    val offsetY = remember { Animatable(0f) }
    val verticalPadding = 30f
    val elementHeight = 70f
    val checkIconSize = 24f

    LaunchedEffect(key1 = state) {
        val newIndex = Settings.AppTheme.entries.indexOf(state.theme)

        val newItemPosition = newIndex * elementHeight + verticalPadding
        val newIconPosition = newItemPosition + elementHeight / 2 + checkIconSize / 2
        offsetY.animateTo(newIconPosition)
    }

    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Selected",
        modifier = Modifier
            .zIndex(1f)
            .offset(x = 16.dp, y = offsetY.value.dp)
            .size(checkIconSize.dp)
    )

    Column(
        modifier = Modifier
            .padding(vertical = verticalPadding.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(verticalPadding.dp)
            )
    ) {
        Settings.AppTheme.entries.forEach { appTheme ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true, color = Color.Gray),
                        onClick = getAction(SettingsActionType.OnChangeAppTheme(appTheme))
                    )
                    .height(elementHeight.dp)
                    .fillMaxWidth()
                    .padding(start = 64.dp)
            ) {
                Text(
                    text = appTheme.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}